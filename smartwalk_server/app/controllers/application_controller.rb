require 'line/bot'

class ApplicationController < ActionController::API
  def line_client()
    @line_client ||= Line::Bot::Client.new { |config|
      config.channel_secret = Rails.configuration.line['channel_secret']
      config.channel_token = Rails.configuration.line['channel_token']
    }
  end
end
