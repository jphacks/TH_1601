class RegistrationToken < ApplicationRecord
  belongs_to :user
  validates :token, presence: true
  validates :token, uniqueness: true

  def self.generate_random_string
    domain =  [('a'..'z'), ('A'..'Z'), ('0'..'9')].map{|i| i.to_a}.flatten
    (0...20).map{domain.to_a[rand(domain.length)] }.join
  end

  def self.generate_token_for(user)
    token_obj = RegistrationToken.new
    token_obj.token = RegistrationToken.generate_random_string
    token_obj.user = user
    token_obj.save!
    token_obj.token
  end
end
