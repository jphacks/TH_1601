Rails.application.routes.draw do
  post 'message/push', format: false

  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
  post 'callback', to: 'callback#callback', format: false
end
