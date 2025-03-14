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
import { useRouter } from "next/router";
import { createDisciplina } from "@/services/secretariaService";

const formSchema = z.object({
  nome: z.string().min(1, {
    message: "Nome deve ter no mínimo 3 caractere.",
  }),
});

export default function Disciplina() {
  const [errorMessage, setErrorMessage] = useState("");
  // const [secretariaId, setSecretariaId] = useState<number | null>(null);

  //   const router = useRouter();

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      nome: "",
    },
  });

  async function onSubmit(values: z.infer<typeof formSchema>) {
    setErrorMessage("");

    try {
      const formData = {
        nome: values.nome,
      };

      const secretariaId = Number(localStorage.getItem("token"));
      console.log("secretariaId:", secretariaId);

      const novaDisciplina = await createDisciplina(secretariaId, formData);

      console.log("nova disciplina:", novaDisciplina);

      if (novaDisciplina) {
        alert("Disciplina criada com sucesso!");
        console.log("nova disciplina:", novaDisciplina);
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
      <h2 className="w-full text-center">Criar Disciplina</h2>
      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit)}
          className="space-y-8 w-full text-center"
        >
          <FormField
            control={form.control}
            name="nome"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Nome</FormLabel>
                <FormControl>
                  <Input placeholder="Nome" {...field} />
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
    </div>
  );
}
