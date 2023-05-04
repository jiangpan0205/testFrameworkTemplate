@tag1

  Feature: First one

    Scenario: First scenario
      Given I open login page
      When I am in the landing page
      Then I login into website
      Then I search item in product page
      Then I add products to shopping cart
      Then I verified items in the shopping cart
      Then I remove 1 T-Shirt that I don't like from the cart
      Then I proceed to checkout and place my order
      Then I add card details to proceed payment
      Then I download invoice after payment successful