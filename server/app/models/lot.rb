class Lot < ActiveRecord::Base
  has_many :spots

  def attributes
    super.merge('num_spots_available' => :num_spots_available)
  end

  def num_spots_available
    self.spots.where('status = ?', Spot::STATUS["AVAILABLE"]).count
  end

end
