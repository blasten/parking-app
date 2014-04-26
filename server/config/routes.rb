Server::Application.routes.draw do

  # Root
  root "lots#index"

  # Errors
  match "/404", to: "errors#not_found", via: :all
  match "/500", to: "errors#exception", via: :all

  # Reports
  get "reports/parking_lot", as: 'parking_lot_report'
  get "reports/user_usage", as: 'user_usage_report'
  post "reports/user_usage_download", as: 'user_usage_download_report'


  # Session handling
  get "sign-in" => "sessions#new", as: 'sign_in'
  get "sign-out" => "sessions#destroy", as: 'sign_out'
  resource :sessions, only: [:create, :destroy, :show]


  resources :lots do
    resources :spots, only: [:new, :create, :edit, :update, :destroy]
  end

  resources :reservations
  resources :users

  get "settings" => "users#edit_me", as: 'settings'


  # RESTFul API
  namespace :api, :defaults => {:format => 'json'} do
    get '/', to: 'base#index'
    resources :users do
      collection do
        get 'me', to: 'users#show_me'
        put 'me', to: 'users#update_me'
      end
    end
    resources :spots
    resources :lots do
      get 'spots', to: 'lots#show_spots'
    end
    resources :reservations
  end
end
