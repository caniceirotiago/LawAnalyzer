package LAproject;

public class Configuration {
    public static final String API_URL = "https://api.openai.com/v1/chat/completions";
    public static final String APIKey = "sk-proj-09lEsvtuz13yJpfnzzAAT3BlbkFJ4Vdg2QiKCalAodrO7ikT";
    public static final String APIModel = "gpt-4o";
    public static final long MIN_FILES_TO_NORMALIZE = 4;
    public static final String APIMainPrompt = "Analyze the following law text and extract all construction parameters." +
            "Reading an law text you have to understand the hierarchy of the titles and subtitle. " +
            "Ensure that numerical values and boolean characteristics are correctly identified. " +
            "Boolean values should be associated with clear characteristics," +
            " for example: 'Permitido construção com contraplacado marítimo (true/false)'. " +
            "The response should be in english JSON format with an array of objects, " +
            "each containing 'property_name', 'value', and 'value_type' (either 'boolean' or 'double'). " +
            "Percentage values should be well identified and the value should be 0 to 1 format." +
            "Example: [{\"property_name\": \"Maximum number of floors for construction\", " +
            "\"value\": 4, \"value_type\": \"double\"}, {\"property_name\": \"Allowed construction with certain type of material\", " +
            "\"value\": true, \"value_type\": \"boolean\"}] \n\n";
    public static final String TEXT_TO_ANALYZE = "Artigo 29.º\n" +
            "Condicionamento da edificabilidade por razões de risco de incêndio rural\n" +
            "1 — Sem prejuízo das medidas de defesa da floresta contra incêndios definidas no quadro\n" +
            "legal em vigor, a construção de novos edifícios ou a ampliação de edifícios existentes obedecem\n" +
            "às seguintes regras definidas no Plano Municipal de Defesa da Floresta Contra Incêndios, adiante\n" +
            "designado de PMDFCI:\n" +
            "a) Fora das áreas edificadas consolidadas, não é permitida a construção de novos edifícios\n" +
            "nas áreas classificadas na cartografia de perigosidade de incêndio rural definida no PMDFCI como\n" +
            "de alta e muito alta perigosidade;\n" +
            "b) A construção de novos edifícios ou a ampliação de edifícios existentes apenas são permitidas\n" +
            "fora das áreas edificadas consolidadas, nas áreas classificadas na cartografia de perigosidade de \n" +
            "N.º 37 22 de fevereiro de 2022 Pág. 336\n" +
            "Diário da República, 2.ª série PARTE H\n" +
            "incêndio rural definida em PMDFCI como de média, baixa e muito baixa perigosidade, desde que\n" +
            "se cumpram, cumulativamente, os seguintes condicionalismos:\n" +
            "i) Garantir, na sua implantação no terreno, a distância à estrema da propriedade de uma faixa\n" +
            "de proteção nunca inferior a 50 m, quando inseridas ou confinantes com terrenos ocupados com\n" +
            "floresta, matos ou pastagens naturais;\n" +
            "ii) Garantir, na sua implantação no terreno, a distância à estrema da propriedade de uma faixa\n" +
            "de proteção, quando inseridas ou confinantes com espaços agrícolas, de:\n" +
            "ii) 1) 20 metros, caso a perigosidade de incêndios seja moderada;\n" +
            "ii) 2) 15 metros, caso a perigosidade de incêndios seja baixa;\n" +
            "ii) 3) 10 metros, caso a perigosidade de incêndios seja muito baixa.\n" +
            "iii) A faixa de proteção deve ser sempre medida a partir da alvenaria exterior da edificação.\n" +
            "c) Adotar medidas relativas à contenção de possíveis fontes de ignição de incêndios no edifício\n" +
            "e nos respetivos acessos;\n" +
            "d) Existência de parecer favorável da CMDF;\n" +
            "e) Quando a faixa de proteção integre rede secundária ou primária estabelecida, infraestruturas\n" +
            "viárias ou planos de água, a área destas pode ser contabilizada na distância mínima exigida para\n" +
            "aquela faixa de proteção.\n" +
            "2 — Para efeitos da aplicação do disposto no número anterior faz -se corresponder as áreas\n" +
            "edificadas consolidadas com o solo urbano e com os aglomerados rurais.";
}
