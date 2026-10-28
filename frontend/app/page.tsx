// app/page.tsx
import React from "react";

interface PageProps {
  params?: Record<string, string>; // tipado genérico para evitar errores
}

export default function Page({ params }: PageProps) {
  return (
    <main>
      <h1>Bienvenido a Fullstack Lab</h1>
      <p>Esta es una página genérica de Next.js con TypeScript.</p>

      {params && (
        <div>
          <h2>Parámetros recibidos:</h2>
          <pre>{JSON.stringify(params, null, 2)}</pre>
        </div>
      )}
    </main>
  );
}
