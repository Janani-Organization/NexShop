/**
 * @vitest-environment jsdom
 */
import { render, screen, fireEvent } from "@testing-library/react"
import "@testing-library/jest-dom/vitest"
import { BrowserRouter } from "react-router-dom"
import { describe, test, expect, vi } from "vitest"
import Home from "./Home"

// Minimal mock to prevent actual API calls during render
vi.mock("../api/productApi", () => ({
  getProducts: vi.fn().mockResolvedValue({ data: { products: [] } })
}))

describe("Home Page Tests", () => {
    test("Hero banner text should exist", () => {
        render(
            <BrowserRouter>
                <Home />
            </BrowserRouter>
        )
        expect(screen.getByText(/Discover Your/i)).toBeInTheDocument()
    })

    test("Search input should exist", () => {
        render(
            <BrowserRouter>
                <Home />
            </BrowserRouter>
        )
        expect(screen.getAllByPlaceholderText("Search products, brands, categories...")[0]).toBeInTheDocument()
    })

    test("User can type in search input", () => {
        render(
            <BrowserRouter>
                <Home />
            </BrowserRouter>
        )
        const searchInput = screen.getAllByPlaceholderText("Search products, brands, categories...")[0]
        fireEvent.change(searchInput, { target: { value: "Laptop" } })
        expect(searchInput.value).toBe("Laptop")
    })
})
