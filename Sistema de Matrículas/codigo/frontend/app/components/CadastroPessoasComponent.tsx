"use client";

import { z } from "zod";

import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { useState } from "react";
import { useRouter } from "next/navigation";
import { createUser, getUserType, login } from "@/services/usuarioService";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import Link from "next/link";

const formSchema = z.object({
  nome: z.string().min(1, {
    message: "Nome deve ter no mínimo 3 caractere.",
  }),
  senha: z.string().min(4, {
    message: "Senha deve ter no mínimo 4 caracteres.",
  }),
  tipo: z.string().min(4, {
    message: "Tipo de usuário deve ser selecionado.",
  }),
});

export default function CadastroPessoasComponent() {
  const [errorMessage, setErrorMessage] = useState("");
  const router = useRouter();

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      nome: "",
      senha: "",
      tipo: "",
    },
  });

async function onSubmit(values: z.infer<typeof formSchema>) {
    setErrorMessage("");

    try {
        const formData = {
            nome: values.nome,
            senha: values.senha,
            tipo: values.tipo,
        };

        const novoUsuario = await createUser(formData);

        if (novoUsuario) {
            alert("Usuário criado com sucesso!");
            console.log("novo usuario:", novoUsuario);
        } else {
            setErrorMessage("Falha ao criar usuário.");
        }
    } catch (error) {
        console.error("Erro ao tentar criar usuário:", error);
        setErrorMessage("Erro ao conectar-se ao servidor.");
    }
}

  return (
    <div className="flex w-full justify-center">
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
          <FormField
            control={form.control}
            name="senha"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Senha</FormLabel>
                <FormControl>
                  <Input placeholder="Senha" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="tipo"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Tipo</FormLabel>
                <Select
                  onValueChange={field.onChange}
                  defaultValue={field.value}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder="Selecione um tipo de usuário" />
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent>
                    <SelectItem value="SECRETARIA">SECRETARIA</SelectItem>
                    <SelectItem value="ALUNO">ALUNO</SelectItem>
                    <SelectItem value="PROFESSOR">PROFESSOR</SelectItem>
                  </SelectContent>
                </Select>
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
