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

export const findAllUser = async (
  page: number = 0,
  pageSize: number = 10
): Promise<Usuario[]> => {
  const response = await api.get<Usuario[]>("/usuario", {
    params: {
      page,
      pageSize,
    },
  });
  return response.data;
};

export const searchUser = async (page: number, pageSize: number, searchTerm: string = "") => {
    const response = await api.get<Usuario[]>("/usuario/search", {
        params: {
            page,
            pageSize,
            query: searchTerm
        }
    })
    
    console.log("response", response.data)
    return response.data;
  };

export const login = async (login: Login): Promise<boolean> => {
  const response = await api.post<Login>("/login", login);

  if (response.data) {
    return true;
  }

  return false;
};

export const getUserType = async (id: number): Promise<string> => {
  const response = await api.get<string>(`/usuario/${id}/tipo`, {
    params: { id },
  });
  return response.data;
};

export const getUserById = async (id: number): Promise<Usuario> => {
  const response = await api.get<Usuario>(`/usuario/${id}`);
  return response.data;
};

export const updateUser = async (
  id: number,
  usuario: Usuario
): Promise<Usuario> => {
  const response = await api.put<Usuario>(`/usuario/${id}`, usuario);
  return response.data;
};

export const deleteUser = async (
    id: number
  ): Promise<Usuario> => {
    const response = await api.delete<Usuario>(`/usuario/${id}`);
    return response.data;
  };
