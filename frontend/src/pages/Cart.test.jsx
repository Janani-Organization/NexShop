/**
 * @vitest-environment jsdom
 */
import { render, screen } from "@testing-library/react"
import "@testing-library/jest-dom/vitest"
import { BrowserRouter } from "react-router-dom"
import { describe, test, expect, vi } from "vitest"
import Cart from "./Cart"
import { AuthContext } from "../context/AuthContext"

// Mock the cart context hook
vi.mock("../context/CartContext", () => ({
  useCart: () => ({
    cartItems: [],
    cartTotal: 0,
    updateQuantity: vi.fn(),
    removeFromCart: vi.fn(),
    clearCart: vi.fn()
  })
}))

describe("Cart Page Tests", () => {
    const mockAuthValue = { user: null }

    const renderCart = () => {
        render(
            <AuthContext.Provider value={mockAuthValue}>
                <BrowserRouter>
                    <Cart />
                </BrowserRouter>
            </AuthContext.Provider>
        )
    }

    test("Should display empty cart message when cart is empty", () => {
        renderCart()
        expect(screen.getByText("Your cart is empty")).toBeInTheDocument()
        expect(screen.getByText("Shop Now")).toBeInTheDocument()
    })
})
