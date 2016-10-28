class MessageController < ApplicationController
  def push
    body = request.body.string
    json = JSON.parse(body)
    # sender = json['sender']
    receiver = json['receiver']
    message = {
      type: 'text',
      text: json['message']
    }
    response = line_client.push_message(receiver, message)

    logger.debug(response.code)
    logger.debug(response.body)

    head :ok
  end
end
