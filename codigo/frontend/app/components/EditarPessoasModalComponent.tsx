"use client";

import { useEffect, useState } from "react";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
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
import { Select, SelectTrigger, SelectValue, SelectContent, SelectItem } from "@/components/ui/select";
import { getUserById, updateUser, Usuario } from "@/services/usuarioService";

const formSchema = z.object({
  nome: z.string().min(1, {
    message: "Nome deve ter no mínimo 3 caracteres.",
  }),
  senha: z.string().min(4, {
    message: "Senha deve ter no mínimo 4 caracteres.",
  }),
  tipo: z.string().min(4, {
    message: "Tipo de usuário deve ser selecionado.",
  }),
});

interface EditarPessoasModalComponentProps {
  userId: number;
  isOpen: boolean;
  onClose: () => void;
}

export default function EditarPessoasModalComponent({ userId, isOpen, onClose }: EditarPessoasModalComponentProps) {
  const [errorMessage, setErrorMessage] = useState("");
  const [loading, setLoading] = useState(true);
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      nome: "",
      senha: "",
      tipo: "",
    },
  });

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const user = await getUserById(userId);
        form.reset({
          nome: user.nome,
          senha: user.senha,
          tipo: user.tipo,
        });
      } catch (error) {
        console.error("Erro ao carregar dados do usuário:", error);
        setErrorMessage("Erro ao carregar dados do usuário.");
      } finally {
        setLoading(false);
      }
    };

    if (isOpen) {
      fetchUser();
    }
  }, [isOpen, userId, form]);

  async function onSubmit(values: z.infer<typeof formSchema>) {
    setErrorMessage("");

    try {
      const updatedUser = await updateUser(userId, values);

      if (updatedUser) {
        alert("Usuário atualizado com sucesso!");
        onClose();
      } else {
        setErrorMessage("Falha ao atualizar usuário.");
      }
    } catch (error) {
      console.error("Erro ao tentar atualizar usuário:", error);
      setErrorMessage("Erro ao conectar-se ao servidor.");
    }
  }

  if (!isOpen) {
    return null;
  }

  return (
    <div className="modal">
      <div className="modal-content w-full ring p-4 rounded-xl block">
        <div className="text-center items-center flex justify-between mb-4">
            <h1 className="text-lg font-semibold">Editar Usuário</h1>
            <span title="Fechar" className="flex items-center gap-1 justify-end close text-3xl hover:cursor-pointer" onClick={onClose}><p className="text-sm">Fechar</p> <p>&times;</p></span>
        </div>
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
              <Button className="hover:cursor-pointer" type="submit">
                Enviar
              </Button>
            </form>
          </Form>
        </div>
      </div>
    </div>
  );
}
