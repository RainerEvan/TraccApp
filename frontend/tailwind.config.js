module.exports = {
    content: [
        "./src/**/*.{html,ts}",
    ],
    theme: {
        extend: {
            fontFamily: {
                montserrat: "'Montserrat', sans-serif",
            },
            fontSize: {},
            fontWeight: {
                regular: 400,
                medium: 500,
                semibold: 600,
                bold: 700
            }
        },
    },
    plugins: [require('@tailwindcss/forms')],
}