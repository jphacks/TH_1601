#! /bin/bash -x
git pull
bin/rails db:migrate
bin/rails unicorn:hardrestart
sudo service nginx restart
