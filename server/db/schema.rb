# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20140426212140) do

  # These are extensions that must be enabled in order to support this database
  enable_extension "plpgsql"

  create_table "lots", force: true do |t|
    t.string   "name"
    t.boolean  "enabled"
    t.integer  "max_wait"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.decimal  "latitude"
    t.decimal  "longitude"
    t.integer  "designation"
  end

  create_table "reports", force: true do |t|
    t.integer  "user_id"
    t.integer  "lot_id"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "reservation_id"
    t.integer  "time"
  end

  add_index "reports", ["lot_id"], name: "index_reports_on_lot_id", using: :btree
  add_index "reports", ["reservation_id"], name: "index_reports_on_reservation_id", using: :btree
  add_index "reports", ["user_id"], name: "index_reports_on_user_id", using: :btree

  create_table "reservations", force: true do |t|
    t.integer  "user_id"
    t.integer  "spot_id"
    t.integer  "status"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "spots", force: true do |t|
    t.decimal  "latitude"
    t.decimal  "longitude"
    t.integer  "status"
    t.integer  "lot_id"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "number"
  end

  create_table "users", force: true do |t|
    t.string   "email"
    t.string   "password"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "name"
    t.string   "lastname"
    t.integer  "role"
    t.string   "remember_token"
  end

end
