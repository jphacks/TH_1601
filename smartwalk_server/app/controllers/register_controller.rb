class RegisterController < ApplicationController
  def show
    @link_target = 'smart-walk://register/' + params[:id]
    render 'show'
  end

  def register
    body = request.body.read
    json = JSON.parse(body)
    token = json['token']
    mid = json['mid']
    user = RegistrationToken.find_by(token: token).user
    user.mid = mid
    user.save
  end
end
