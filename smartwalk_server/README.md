# README

This README would normally document whatever steps are necessary to get the
application up and running.

Things you may want to cover:

* Ruby version

* System dependencies

* Configuration

* Database creation

* Database initialization

* How to run the test suite

* Services (job queues, cache servers, search engines, etc.)

* Deployment instructions

* ...

## How to install

```
# Move to project directory.
cd smartwalk_server

# Install dependencies.
bundle install --path vendor/bundle

# Copy example settings for line.
cp config/line.yml.example config/line.yml

# Put your LINE's "channel_secret" and "channel_token".
editor config/line.yml
```
