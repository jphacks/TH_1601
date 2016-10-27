require 'test_helper'

class CallbackControllerTest < ActionDispatch::IntegrationTest
  # test "the truth" do
  #   aoossert true
  # end
  test "bad request" do
    post callback_url
    assert_response :success
  end
end
