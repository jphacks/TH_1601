class MessageController < ApplicationController
  def push
    body = request.body.read
    json = JSON.parse(body)
    sender_user_id = json['sender']
    display_name = json['display_name']
    receiver = json['receiver']
    unless receiver then
      begin
        users = User.find_by_sql(["select other.user_id from users as own " +
                                  "inner join friendships as relation " +
                                  "on own.id = relation.user_id " +
                                  "inner join users as other " +
                                  "on relation.friend_user_id = other.id " +
                                  "where other.display_name = ? and " +
                                  "own.mid = ? limit 1", display_name, sender_user_id])
        receiver = users.first.user_id
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
end
