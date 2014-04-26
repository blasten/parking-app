require 'test_helper'

class ReportsControllerTest < ActionController::TestCase
  test "should get parking-lot" do
    get :parking-lot
    assert_response :success
  end

  test "should get user-usage" do
    get :user-usage
    assert_response :success
  end

end
