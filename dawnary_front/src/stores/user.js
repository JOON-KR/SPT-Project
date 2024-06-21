// src/stores/user.js
import create from "zustand";

const useUserStore = create((set) => ({
  allUserEmail: [],
  setAllUserEmail: (emails) => set({ allUserEmail: emails }),
}));

export default useUserStore;
