class Lot < ActiveRecord::Base
  has_many :spots
  validates :name, presence: true, length: { within: 2..10 }
  validates :max_wait, numericality: {only_integer: true, :greater_than_or_equal_to => 0}
  before_save :set_enabled
  before_save :update_spot

  before_destroy :delete_spots


  def attributes
    super.merge('num_spots_available' => :num_spots_available)
  end

  def num_spots_available
    self.spots.where('status = ?', Spot::STATUS["AVAILABLE"]).count
  end

  private
    def set_enabled
     self.enabled = true if self.enabled.nil?
    end

    def update_spot
      if not self.spots.nil?
        if enabled
          self.spots.update_all(:status => Spot::STATUS["AVAILABLE"])
        else
          self.spots.update_all(:status => Spot::STATUS["UNAVAILABLE"])
        end
      end
    end

    def delete_spots
      self.spots.destroy_all
    end
end
