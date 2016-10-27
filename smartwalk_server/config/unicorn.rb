# -*- coding: utf-8 -*-

rails_root = File.expand_path('../../', __FILE__)

worker_processes 2
timeout 8
preload_app true

listen File.expand_path("tmp/sockets/smartwalk-unicorn.socket", rails_root)
pid File.expand_path("tmp/pids/unicorn.pid", rails_root)

before_fork do |server, worker|
  Signal.trap 'TERM' do
    puts 'Unicorn master intercepting TERM and sending myself QUIT instead'
    Process.kill 'QUIT', Process.pid
  end

  defined?(ActiveRecord::Base) and
    ActiveRecord::Base.connection.disconnect!
end

after_fork do |server, worker|
  Signal.trap 'TERM' do
    puts 'Unicorn worker intecepting TERM and doing nothing. Wait for master to send QUIT'
  end

  defined?(ActiveRecord::Base) and
    ActiveRecord::Base.establish_connection
end

stderr_path File.expand_path('log/unicorn.log', rails_root) 
stdout_path File.expand_path('log/unicorn.log', rails_root)
