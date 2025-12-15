export type LoginData = {
    accessToken: string;
    refreshToken: string;
    id: number;
    username: string;
}

export type RegisterData = {
    id: number;
    username: string;
    email: string;
    phoneNumber: string;
    password: string | null;
}

