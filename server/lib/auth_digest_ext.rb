module ActionController
  module HttpAuthentication
    module Digest
      def authentication_request_with_customization(controller, realm, message = nil)
        message = controller.respond_with({error: "Invalid access"}, :location => "/")
        authentication_request_without_customization(controller, realm, message)
      end
      alias_method_chain :authentication_request, :customization
    end
  end
end