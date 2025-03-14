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
import { getUserType, login } from "@/services/usuarioService";

const formSchema = z.object({
  id: z.string().min(1, {
    message: "ID deve ter no mínimo 1 caractere.",
  }),
  senha: z.string().min(4, {
    message: "Senha deve ter no mínimo 4 caracteres.",
  }),
});

export default function Login() {
  const [errorMessage, setErrorMessage] = useState("");
  const router = useRouter();

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      id: "",
      senha: "",
    },
  });

  async function onSubmit(values: z.infer<typeof formSchema>) {
    setErrorMessage("");

    try {
      const loginData = {
        id: Number(values.id),
        senha: values.senha,
      };

      const isValid = await login(loginData);

      if (isValid) {
        const userType = await getUserType(loginData.id);

        console.log("Login bem-sucedido!");

        localStorage.setItem("token", loginData.id.toString());

        console.log("Tipo do usuário:", userType);

        if (userType === "secretaria".toUpperCase()) {
            router.push('/secretaria');
        } else if (userType === "aluno".toUpperCase()) {
            router.push('/aluno');
        } else if (userType === "professor".toUpperCase()) {
            router.push('/professor');
        }
      } else {
        setErrorMessage("Usuário ou senha inválidos.");
      }
    } catch (error) {
      console.error("Erro ao tentar fazer login:", error);
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
            name="id"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Matrícula</FormLabel>
                <FormControl>
                  <Input placeholder="123456" {...field} />
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
          <Button className="hover:cursor-pointer" type="submit">
            Submit
          </Button>
        </form>
      </Form>
    </div>
  );
}
