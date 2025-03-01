'use client';

import { Button } from "@/components/ui/button";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
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
  senha: z.string().min(4, {
    message: "Senha deve ter no mínimo 4 caracteres.",
  }),
  tipo: z.string().min(4, {
    message: "Tipo de usuário deve ser selecionado.",
  }),
});

export default function Disciplina() {
    const [errorMessage, setErrorMessage] = useState("");
    const [secretariaId, setSecretariaId] = useState<number | null>(null);

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

            const secretariaId = Number(localStorage.getItem("token"));
            console.log("secretariaId:", secretariaId);
    
            const novoUsuario = await createDisciplina(secretariaId, formData);
    
            if (novoUsuario) {
                alert("Usuário criado com sucesso!");
                console.log("novo usuario:", novoUsuario);
                form.reset();
            } else {
                setErrorMessage("Falha ao criar usuário.");
            }
        } catch (error) {
            console.error("Erro ao tentar criar usuário:", error);
            setErrorMessage("Erro ao conectar-se ao servidor.");
        }
    }

    
    return (
        <div>
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