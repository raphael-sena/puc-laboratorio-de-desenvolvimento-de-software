"use client";

import { useEffect, useState } from "react";
import { findAllUser, getUserType, Usuario, searchUser, deleteUser } from "@/services/usuarioService";
import EditarPessoasModalComponent from "./EditarPessoasModalComponent";

import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableFooter,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";

import { FaGear, FaTrash } from "react-icons/fa6";

export default function GerenciarPessoasComponent() {
  const [users, setUsers] = useState<Usuario[]>([]);
  const [userTypes, setUserTypes] = useState<{ [key: number]: string }>({});
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [isEditModalOpen, setIsEditModalOpen] = useState<boolean>(false);
  const [selectedUserId, setSelectedUserId] = useState<number | null>(null);
  const [currentPage, setCurrentPage] = useState<number>(0);
  const [pageSize, setPageSize] = useState<number>(6);
  const [searchTerm, setSearchTerm] = useState<string>("");

  const fetchUsers = async (page: number, pageSize: number) => {
    try {
      const usersData = await findAllUser(page, pageSize);
      setUsers(usersData);
    } catch (err) {
      setError("Erro ao buscar usuários.");
    } finally {
      setLoading(false);
    }
  };

  const fetchUserType = async (id: number) => {
    try {
      const userTypeData = await getUserType(id);
      setUserTypes((prevUserTypes) => ({
        ...prevUserTypes,
        [id]: userTypeData,
      }));
      console.log("Tipo de usuário:", userTypeData);
    } catch (err) {
      console.error("Erro ao buscar tipo de usuário:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    users.forEach((user) => {
      if (user.id !== undefined) {
        fetchUserType(user.id);
      }
    });
    fetchUsers(currentPage, pageSize);
  }, [currentPage, pageSize]);

  useEffect(() => {
      users.forEach((user) => {
        if (user.id !== undefined) {
          fetchUserType(user.id);
        }
      });
    }, [users]);

  const handleEditUser = (id: number) => {
    setSelectedUserId(id);
    setIsEditModalOpen(true);
  };

  const handleDeleteUser = async (id: number) => {
    try {
      const resposta = confirm("Tem certeza que deseja deletar este usuário?");
      if (!resposta) return;
  
      await deleteUser(id);
  
      alert("Usuário deletado com sucesso!");
      
      fetchUsers(currentPage, pageSize);
      
    } catch (error) {
      console.error("Erro ao deletar usuário:", error);
    }
  };

  const handleCloseModal = () => {
    fetchUsers(currentPage, pageSize);
    setIsEditModalOpen(false);
    setSelectedUserId(null);
  };

  const handleNextPage = () => {
    setCurrentPage((prevPage) => prevPage + 1);
  };

  const handlePreviousPage = () => {
    setCurrentPage((prevPage) => Math.max(prevPage - 1, 0));
  };

  const handleSearch = async () => {
    setLoading(true);
  
    try {
      const usersData = await searchUser(currentPage, pageSize, searchTerm);
      console.log(usersData);
      setUsers(usersData);
    } catch (err) {
      console.error("Erro ao buscar usuários:", err);
      setError("Erro ao buscar usuários.");
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div>Carregando...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  return (
    <div>
      <div className="flex justify-between items-center mb-4">
        <h1 className="text-xl mb-2 font-semibold">Gerenciar Pessoas</h1>
        <div className="flex w-full max-w-sm items-center space-x-2">
          <Input
            type="search"
            placeholder="Pesquisar por ID..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
          <Button className="hover:cursor-pointer" onClick={handleSearch}>
            Pesquisar
          </Button>
        </div>
      </div>
      <div className="flex gap-4 justify-between">
        <Table className="min-w-[700px]">
          <TableCaption>Lista de Usuários.</TableCaption>
          <TableHeader>
            <TableRow>
              <TableHead className="w-[100px]">Id</TableHead>
              <TableHead>Nome</TableHead>
              <TableHead>Tipo</TableHead>
              <TableHead className="text-right">Opções</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {users.map((user) => (
              <TableRow key={user.id}>
                <TableCell className="font-medium">{user.id}</TableCell>
                <TableCell>{user.nome}</TableCell>
                <TableCell>{user.tipo.toUpperCase()}</TableCell>
                <TableCell className="text-right justify-end flex gap-2">
                  <Button
                    className="hover:cursor-pointer"
                    title="Editar"
                    variant="outline"
                    onClick={() => user.id !== undefined && handleEditUser(user.id)}
                  >
                    <FaGear />
                  </Button>
                  <Button
                    className="hover:cursor-pointer"
                    title="Excluir"
                    variant="destructive"
                    onClick={() => user.id !== undefined && handleDeleteUser(user.id)}
                  >
                    <FaTrash />
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>

        {isEditModalOpen && selectedUserId !== null && (
          <EditarPessoasModalComponent
            userId={selectedUserId}
            isOpen={isEditModalOpen}
            onClose={handleCloseModal}
          />
        )}
      </div>
      <div className="flex justify-between mt-4">
        <Button onClick={handlePreviousPage} disabled={currentPage === 0}>
          Página Anterior
        </Button>
        <Button onClick={handleNextPage}>Próxima Página</Button>
      </div>
    </div>
  );
}
