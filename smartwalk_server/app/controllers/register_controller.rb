class RegisterController < ApplicationController
  def show
    @link_target = 'smart-walk://register/' + params[:id]
    render 'show'
  end

  def register
    body = request.body.read
    json = JSON.parse(body)
    user_id = json['id']
    mid = json['mid']
    user = RegistrationToken.find_by(user_id).user
    user.mid = mid
    user.save
  end
end
