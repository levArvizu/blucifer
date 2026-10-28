/** @type {import('next').NextConfig} */
const nextConfig = {
    async rewrites() {
        return [
            {
                source: "/auth/:path*",               // cualquier petición que empiece con /api
                destination: "http://localhost:8081/:path*", // se redirige al backend
            },
        ];
    },
};

module.exports = nextConfig;
