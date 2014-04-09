class Lot < ActiveRecord::Base
  has_many :spots
  validates :name, presence: true, length: { within: 2..10 }
  validates :max_wait, numericality: {only_integer: true, :greater_than_or_equal_to => 0}

  def attributes
    super.merge('num_spots_available' => :num_spots_available)
  end

  def num_spots_available
    self.spots.where('status = ?', Spot::STATUS["AVAILABLE"]).count
  end

end
