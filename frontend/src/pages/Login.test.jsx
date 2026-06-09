/**
 * @vitest-environment jsdom
 */
import { render, screen, fireEvent } from "@testing-library/react"
import "@testing-library/jest-dom/vitest"
import { BrowserRouter } from "react-router-dom"
import { describe, test, expect, vi } from "vitest"
import Login from "./Login"
import { AuthContext } from "../context/AuthContext"

describe("Login Page Tests", () => {
    const mockAuthValue = { login: vi.fn() }

    const renderLogin = () => {
        render(
            <AuthContext.Provider value={mockAuthValue}>
                <BrowserRouter>
                    <Login />
                </BrowserRouter>
            </AuthContext.Provider>
        )
    }

    test("Sign In button should exist", () => {
        renderLogin()
        expect(screen.getByRole("button", { name: "Sign In" })).toBeInTheDocument()
    })

    test("Email input should exist", () => {
        renderLogin()
        expect(screen.getAllByPlaceholderText("you@example.com")[0]).toBeInTheDocument()
    })

    test("Password input should exist", () => {
        renderLogin()
        expect(screen.getAllByPlaceholderText("••••••••")[0]).toBeInTheDocument()
    })

    test("User can type in Email input", () => {
        renderLogin()
        const emailInput = screen.getAllByPlaceholderText("you@example.com")[0]
        fireEvent.change(emailInput, { target: { value: "test@example.com" } })
        expect(emailInput.value).toBe("test@example.com")
    })
})
