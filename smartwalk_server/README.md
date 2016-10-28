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
# Install dependencies.
sudo apt install ruby ruby-dev nginx
sudo gem install bundler

# Clone project
cd /var/nginx
sudo git clone https://github.com/jphacks/TH_1601.git
sudo chown -R yourusername:yourusername TH_1601

# Move to project directory.
cd TH_1601/smartwalk_server

# Install gem dependencies.
bundle install --path vendor/bundle

# Copy example settings for line.
cp config/line.yml.example config/line.yml

# Put your LINE's "channel_secret" and "channel_token" into "line.yml".
editor config/line.yml

# Copy nginx configuration file from the sample.
cp lib/support/nginx/smartwalk /etc/nginx/sites-available/smartwalk

# Edit nginx settings to adapt your server configuration.
editor /etc/nginx/sites-available/smartwalk

# Enable your nginx settings.
ln -s /etc/nginx/sites-available/smartwalk /etc/nginx/sites-enabled/smartwalk

# Run database migration.
bin/rails db:migrate

# Run unicorn.
cd /var/nginx/TH_1601/smartwalk_server
bin/rails unicorn:start

# Run nginx.
sudo service nginx restart
```

## Exposed API
+ POST /message/push

JSON example:

```
{
	"sender": "ユーザー識別子",
#	"receiver": "表示名",
    "receiver": "ユーザー識別子"
	"message": "送信するメッセージ"
}
```
