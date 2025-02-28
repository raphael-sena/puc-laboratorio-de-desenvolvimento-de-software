import api from "../utils/axiosInstance";

export interface Usuario {
  id?: number;
  nome: string;
  senha: string;
  tipo: string;
}

export interface Login {
    id?: number;
    senha: string;
  }

export const createUser = async (usuario: Usuario): Promise<Usuario> => {
  const response = await api.post<Usuario>("/usuario", usuario);
  return response.data;
};

export const findAllUser = async (): Promise<Usuario[]> => {
  const response = await api.get<Usuario[]>("/usuario");
  return response.data;
};

export const login = async (login: Login): Promise<boolean> => {
    const response = await api.post<Login>("/login", login);

    if (response.data) {
        return true;
    }

    return false;
}

export const getUserType = async (id: number): Promise<string> => {
    const response = await api.get<string>(`/usuario/${id}/tipo`, { params: { id } });
    return response.data;
};
