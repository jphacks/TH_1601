require 'test_helper'

class UsersControllerTest < ActionDispatch::IntegrationTest
  test "should get friend" do
    get users_friend_url
    assert_response :success
  end

end
