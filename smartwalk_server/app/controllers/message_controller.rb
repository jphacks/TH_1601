class MessageController < ApplicationController
  def push
    body = request.body.read
    json = JSON.parse(body)
    sender = json['sender']
    display_name = json['displayName']
    receiver = json['receiver']
    unless receiver then
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
