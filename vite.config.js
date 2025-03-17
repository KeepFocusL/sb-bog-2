import {defineConfig} from "vite";
import path from "path";

export default defineConfig({
    plugins: [],
    build: {
        manifest: true,
        outDir: path.resolve(__dirname, "src/main/resources/static/build"),
        emptyOutDir: true,
        rollupOptions: {
            input: {
                app: path.resolve(__dirname, "src/main/resources/static/js/app.js"),
            },
            output: {
                entryFileNames: `assets/[name].js`,
                chunkFileNames: `assets/[name].js`,
                assetFileNames: `assets/[name].[ext]`
            }
        },
    },
});
