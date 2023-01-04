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
                extralight:200,
                light: 300,
                regular: 400,
                medium: 500,
                semibold: 600,
                bold: 700
            },
            colors: {
                bca: {
                    100:'#CCEAFF',
                    200:'#99D5FF',
                    300:'#4DB5FF',
                    400:'#0086E6',
                    500:'#0066AE',
                    600:'#004A80',
                    700:'#002D4D',
                },
            },
        },
    },
}