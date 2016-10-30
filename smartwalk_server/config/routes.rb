Rails.application.routes.draw do
  get 'users/friend/:id', to: 'users#friend', format: false
  post 'users/friend', to: 'users#friend_add', format: false

  get 'register/:id', to: 'register#show', format: false
  post 'register', to: 'register#register', format: false

  post 'message/push', format: false

  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
  post 'callback', to: 'callback#callback', format: false
end
