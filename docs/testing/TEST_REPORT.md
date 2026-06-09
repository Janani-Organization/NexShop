# NexShop Test Report

## 1. Testing Overview
This document outlines the unit testing coverage and execution results for the NexShop application. The backend testing ensures the reliability of core business operations across microservices, while frontend testing verifies that the React components accurately render the user interface and handle basic interactions correctly.

## 2. Frameworks Used

**Backend:**
- JUnit 5
- Mockito
- JaCoCo

**Frontend:**
- Vitest
- React Testing Library

## 3. Backend Test Cases Table

| Service | Test Case | Status |
|---|---|---|
| user-service | `register_Success` | Passed ✅ |
| user-service | `login_Success` | Passed ✅ |
| product-service | `getAllProducts_Success` | Passed ✅ |
| product-service | `searchProducts_Success` | Passed ✅ |
| cart-service | `addToCart_Success` | Passed ✅ |
| cart-service | `removeItem_Success` | Passed ✅ |
| order-service | `placeOrder_Success` | Passed ✅ |
| AI-service | `handleQuery_SearchIntent_Success` | Passed ✅ |

## 4. Frontend Test Cases Table

| Component | Test Purpose | Status |
|---|---|---|
| `Register.test.jsx` | Verify rendering of form elements, inputs, and visibility toggles | Passed ✅ |
| `Login.test.jsx` | Verify rendering of email/password inputs and sign in interactions | Passed ✅ |
| `Home.test.jsx` | Verify rendering of hero banner and search input typing interactions | Passed ✅ |
| `Cart.test.jsx` | Verify rendering of the empty cart state message | Passed ✅ |

## 5. Test Execution Summary

**Backend:**
- 8 tests passed

**Frontend:**
- 16 tests passed

**JaCoCo:**
- Coverage reports generated successfully for all backend microservices.

## 6. JaCoCo Report Locations

- `user-service/target/site/jacoco/index.html`
- `product-service/target/site/jacoco/index.html`
- `cart-service/target/site/jacoco/index.html`
- `order-service/target/site/jacoco/index.html`
- `AI-service/target/site/jacoco/index.html`

## 7. Conclusion

- Production code was not modified.
- Tests are isolated using mocks.
- All tests passed successfully.
- JaCoCo coverage reports were generated successfully.
