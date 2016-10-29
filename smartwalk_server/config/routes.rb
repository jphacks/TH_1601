Rails.application.routes.draw do
  get 'register/:id', to: 'register#show', format: false
  post 'register/:id', to: 'register#register', format: false

  post 'message/push', format: false

  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
  post 'callback', to: 'callback#callback', format: false
end
