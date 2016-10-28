class CallbackController < ApplicationController
  def callback
    body = request.body.string
    signature = request.env['HTTP_X_LINE_SIGNATURE']
    unless line_client.validate_signature(body, signature)
      logger.info 'Bad request'
      return head :ok
    end

    events = line_client.parse_events_from(body)
    events.each do |event|
      case event
      when Line::Bot::Event::Message
        case event.type
        when Line::Bot::Event::MessageType::Text
          message = {
            type: 'text',
            text: event.message['text']
          }
          line_client.reply_message(event['replyToken'], message)
        when Line::Bot::Event::MessageType::Image,
             Line::Bot::Event::MessageType::Video
          response = line_client.get_message_content(event.message['id'])
          tf = Tempfile.open('content')
          tf.write(response.body)
        end
      end
    end
    head :ok
  end
end
