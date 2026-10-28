"use client";

import { useState } from "react";
import axios from "axios";
import styles from "./orders.module.css";

interface OrderResponseDTO {
    amount: number;
    status: string;
}

export default function OrdersPage() {

    const [amount, setAmount] =
        useState("");

    const [result, setResult] =
        useState<OrderResponseDTO | null>(null);

    const [loading, setLoading] =
        useState(false);

    const [error, setError] =
        useState("");

    const approveOrder = async () => {

        try {

            setLoading(true);
            setError("");

            const response =
                await axios.post<OrderResponseDTO>(
                    `http://localhost:8081/api/orders/approve?amount=${amount}`
                );

            setResult(response.data);

        } catch (err) {

            console.error(err);

            setError(
                "Error al ejecutar BPMN"
            );

        } finally {

            setLoading(false);

        }

    };

    return (

        <div style={{ padding: "2rem" }}>

            <h1>
                Simulador BPMN
            </h1>

            <input
                type="number"
                placeholder="Monto"
                value={amount}
                onChange={(e) =>
                    setAmount(e.target.value)
                }
            />

            <button
                onClick={approveOrder}
            >
                Procesar Pedido
            </button>

            {loading &&
                <p>
                    Ejecutando BPMN...
                </p>
            }

            {error &&
                <p>
                    {error}
                </p>
            }

            {result && (

                <div>

                    <h3>
                        Resultado
                    </h3>

                    <p>
                        Monto:
                        {result.amount}
                    </p>

                    <p>
                        Estado:
                        {result.status}
                    </p>

                </div>

            )}

        </div>

    );
}