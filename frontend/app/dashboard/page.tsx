"use client";
import { useEffect, useState } from "react";
import axios from "axios";
import { useRouter } from "next/navigation";
import "./dashboard.css";
import Cookies from "js-cookie";

export default function DashboardPage() {
    const [data, setData] = useState<any>(null);
    const [error, setError] = useState("");
    const router = useRouter();

    useEffect(() => {
        const token = localStorage.getItem("token");
        const roles = JSON.parse(localStorage.getItem("roles") || "[]");

        if (!token) {
            router.push("/login");
            return;
        }

        // 🔹 Validar rol antes de llamar al backend
        if (!roles.includes("ADMIN")) {
            setError("Acceso denegado: se requiere rol ADMIN");
            return;
        }

        const fetchData = async () => {
            try {
                const response = await axios.get("/auth/admin/dashboard", {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                setData(response.data);
            } catch (err: any) {
                setError("No autorizado o token inválido");
            }
        };

        fetchData();
    }, [router]);

    const handleLogout = () => {
        localStorage.removeItem("token");
        localStorage.removeItem("roles");
        Cookies.remove("token");
        router.push("/login");
    };

    return (
        <div style={{ maxWidth: "600px", margin: "auto", padding: "2rem" }}>
            <button onClick={handleLogout}>Cerrar sesión</button>
            <h2>Dashboard</h2>
            {error && <p style={{ color: "red" }}>{error}</p>}
            {data ? (
                <pre>{JSON.stringify(data, null, 2)}</pre>
            ) : (
                !error && <p>Cargando datos...</p>
            )}
        </div>
    );
}
