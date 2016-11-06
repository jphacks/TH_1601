class MessageController < ApplicationController
  def push
    body = request.body.read
    json = JSON.parse(body)
    sender_mid = json['sender']
    display_name = json['display_name']
    receiver = json['receiver']
    unless receiver then
      begin
        user = User.select_first_friend_of(sender_mid, display_name)
        receiver = user.user_id
#        receiver = User.find_by(user_id: sender_user_id)
#                   .friends.find_by(display_name: display_name).take.user_id
      rescue
        logger.debug("Cannot determine receiver")
        return head :bad_request
      end
    end

    msg_str = json['message']
    Dir.chdir("../period_putter") do
      msg_str = `python3 period_putter.py "#{json['message']}"`
    end

    message = {
      type: 'text',
      text: msg_str
    }

    logger.debug("Start pushing message...")

    response = line_client.push_message(receiver, message)

    logger.debug(response.code)
    logger.debug(response.body)

    head :ok
  end

  def can_push()
    body = request.body.read
    json = JSON.parse(body)
    sender_mid = json['sender']
    display_name = json['display_name']
    count = User.count_friends_of(sender_mid, display_name)
    result = { "can_push" => count == 1 }
    render json: result
  end
end
