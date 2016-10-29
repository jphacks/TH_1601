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
    RegistrationToken.transaction do
      reg_token = RegistrationToken.find_by(token: token)
      user = reg_token.user
      user.mid = mid
      user.save!
      reg_token.destroy!
    end
    head :ok
  end
end
