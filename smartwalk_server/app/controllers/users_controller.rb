class UsersController < ApplicationController
  def friend
    friend_token = params[:id]
    user = User.find_by(friend_token: friend_token)
    @display_name = user.display_name
    @link_target = user.friend_api_url
  end

  def friend_add
    body = request.body.read
    json = JSON.parse(body)
    friend_token = json['friend_token']
    mid = json['mid']
    User.transaction do
      user1 = User.find_by!(friend_token: friend_token)
      user2 = User.find_by!(mid: mid)
      User.make_friend(user1, user2)
      user1.save!
      user2.save!
    end
  end
end
