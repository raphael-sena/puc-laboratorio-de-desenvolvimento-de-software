"use client"; // Caso você esteja em um projeto Next.js com App Router

import React from "react";

interface AlertProps {
  /** Mensagem a ser exibida no alerta */
  message: string;
  /** Variação de estilo para o alerta (info, success, warning, error) */
  variant?: "info" | "success" | "warning" | "error";
  /** Indica se o alerta pode ser fechado */
  closable?: boolean;
  /** Callback a ser chamado ao clicar no "fechar" (se closable = true) */
  onClose?: () => void;
}

const variantStyles = {
  info: "bg-blue-100 text-blue-800 border-blue-300",
  success: "bg-green-100 text-green-800 border-green-300",
  warning: "bg-yellow-100 text-yellow-800 border-yellow-300",
  error: "bg-red-100 text-red-800 border-red-300",
};

export default function Alert({
  message,
  variant = "info",
  closable = false,
  onClose,
}: AlertProps) {
  // Define classes conforme a variação
  const classes = variantStyles[variant];

  return (
    <div className={`border p-3 rounded-md mb-3 ${classes} relative`}>
      <span className="block">{message}</span>
      {closable && onClose && (
        <button
          className="absolute top-2 right-2 text-sm text-inherit hover:opacity-80"
          onClick={onClose}
        >
          &times;
        </button>
      )}
    </div>
  );
}
