class MessageController < ApplicationController
  def push
    body = request.body.read
    json = JSON.parse(body)
    sender_user_id = json['sender']
    display_name = json['display_name']
    receiver = json['receiver']
    unless receiver then
      begin
        sender = User.find_by(user_id: sender_user_id)
        receiver = sender.friends.where(display_name: display_name).take.user_id
      rescue
        logger.debug("Cannot determine receiver")
        return head :bad_request
      end
    end
    message = {
      type: 'text',
      text: json['message']
    }

    logger.debug("Start pushing message...")

    response = line_client.push_message(receiver, message)

    logger.debug(response.code)
    logger.debug(response.body)

    head :ok
  end
end
