class MessageController < ApplicationController
  def push
    json = JSON.parse(request.body.string)
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
