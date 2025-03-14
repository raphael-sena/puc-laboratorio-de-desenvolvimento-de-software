"use client";

import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";

// Importe seu serviço real
import { createDisciplina } from "@/services/secretariaService";

const formSchema = z.object({
  nome: z.string().min(3, { message: "Nome deve ter no mínimo 3 caracteres." }),
  tipo: z.enum(["OBRIGATORIA", "OPTATIVA"], {
    errorMap: () => ({ message: "Selecione um tipo válido" }),
  }),
});

type FormData = z.infer<typeof formSchema>;

export default function Disciplina() {
  const [errorMessage, setErrorMessage] = useState("");

  // Hook Form
  const form = useForm<FormData>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      nome: "",
      tipo: "OBRIGATORIA",
    },
  });

  async function onSubmit(values: FormData) {
    setErrorMessage("");

    try {
      // Pega o ID da secretaria do localStorage
      const secretariaId = Number(localStorage.getItem("token"));
      console.log("secretariaId:", secretariaId);

      // Cria a disciplina
      const novaDisciplina = await createDisciplina(secretariaId, values);

      if (novaDisciplina) {
        alert("Disciplina criada com sucesso!");
        form.reset();
      } else {
        setErrorMessage("Falha ao criar disciplina.");
      }
    } catch (error) {
      console.error("Erro ao tentar criar disciplina:", error);
      setErrorMessage("Erro ao conectar-se ao servidor.");
    }
  }

  return (
    <div className="border border-black p-2 rounded-sm">
      <h2 className="w-full text-center mb-4">Criar Disciplina</h2>

      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit)}
          className="space-y-8 w-full text-center"
        >
          {/* Campo: Nome */}
          <FormField
            control={form.control}
            name="nome"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Nome</FormLabel>
                <FormControl>
                  <Input placeholder="Nome da Disciplina" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

          {/* Campo: Tipo */}
          <FormField
            control={form.control}
            name="tipo"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Tipo</FormLabel>
                <FormControl>
                  <Select
                    onValueChange={field.onChange}
                    defaultValue={field.value}
                  >
                    <SelectTrigger>
                      <SelectValue placeholder="Selecione o tipo" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="OBRIGATORIA">OBRIGATORIA</SelectItem>
                      <SelectItem value="OPTATIVA">OPTATIVA</SelectItem>
                    </SelectContent>
                  </Select>
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

          <Button className="hover:cursor-pointer w-full" type="submit">
            Enviar
          </Button>
        </form>
      </Form>

      {errorMessage && <p className="text-red-500 mt-2">{errorMessage}</p>}
    </div>
  );
}
