require 'test_helper'

class MessageControllerTest < ActionDispatch::IntegrationTest
  test "should get push" do
    get message_push_url
    assert_response :success
  end

end
