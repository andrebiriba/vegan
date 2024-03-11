package app.vegan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterSellerActivity extends AppCompatActivity {

    private BillingClient billingClient;
    TextView sts, txtPrice, desc, tvSub,  poli, termos;
    Button btn_sub;
    String response, des, sku;
    boolean isSuccess = false;

    private ProgressDialog progressDialog;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;
    private String[] cameraPermissions;
    private String[] storagePermissions;
    private Uri image_uri;

    private Context context;
    public static final String TAG = "TAG";
    public static final String TAG1 = "TAG";
    private ImageButton imageButtonBack;
    private ImageView shopPhoto;
    EditText user_name, user_address_neighborhood, user_address_street, user_address_number, user_reference_point,
            user_phone, user_email , user_password, user_reference_cep;
    RadioGroup tradioGroup;
    RadioButton bxDiet, bxPharma, bxCloset, bxOthers, bxServices;
    ImageButton registerbtn;
    TextView backBtn, seller_city, seller_state, seller_country;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID, type, paiscountry;
    private String titleP, textP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_seller);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sts = findViewById(R.id.tv_substs);
        btn_sub = findViewById(R.id.btn_sub);
        txtPrice = findViewById(R.id.txtprice);
        desc = findViewById(R.id.tv_benefit);
        tvSub = findViewById(R.id.tv_subid);
        poli = findViewById(R.id.poli);
        termos = findViewById(R.id.warning);
        user_reference_cep = findViewById(R.id.user_reference_cep);
        shopPhoto = findViewById(R.id.shopIconIv);
        tradioGroup = findViewById(R.id.radioGroup);
        bxDiet = findViewById(R.id.bxDiet);
        bxPharma = findViewById(R.id.bxPharma);
        bxCloset = findViewById(R.id.bxCloset);
        bxOthers = findViewById(R.id.bxOthers);
        bxServices = findViewById(R.id.bxServices);
        imageButtonBack = findViewById(R.id.imageButtonBack);
        seller_city = findViewById(R.id.textview_user_city);
        seller_state = findViewById(R.id.textview_user_estate);
        seller_country = findViewById(R.id.textview_user_country);
        user_name = findViewById(R.id.user_name);
        user_address_neighborhood = findViewById(R.id.user_address_neighborhood);
        user_address_street = findViewById(R.id.user_address_street);
        user_address_number = findViewById(R.id.user_address_number);
        user_reference_point = findViewById(R.id.user_reference_point);
        user_phone = findViewById(R.id.celphoneEt);
        user_email = findViewById(R.id.user_email);
        user_password = findViewById(R.id.user_password);
        registerbtn = findViewById(R.id.registerbtn);
        backBtn = findViewById(R.id.backBtn);
        progressBar = findViewById(R.id.progressBar);

        if (bxDiet.isChecked()) {
            type = "Diet";
        }
        if (bxPharma.isChecked()) {
            type = "Pharmacy";
        }
        if (bxCloset.isChecked()) {
            type = "Closet";
        }
        if (bxOthers.isChecked()) {
            type = "Others";
        }
        if (bxServices.isChecked()) {
            type = "Services";
        }

        billingClient = BillingClient.newBuilder(RegisterSellerActivity.this)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();
        if(ConnectionClass.premium){
            sts.setText("Status: Already Subscribed");
            btn_sub.setVisibility(View.GONE);
        }else{
            sts.setText("Status: Not Subscribed");
        }
        getPrice();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        poli.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterSellerActivity.this);
                builder.setTitle(getString(R.string.politicadegarantia));
                builder.setMessage(getString(R.string.longapolitica));

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        termos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterSellerActivity.this);
                builder.setTitle(getString(R.string.termosdes));
                builder.setMessage("TERMOS E CONTRATO DE PRESTAÇÃO DE SERVIÇOS \n" +
                        "SERVIÇO MARKETPLACE\n" +
                        "                                   \n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "Por esse instrumento particular, de um lado\n" +
                        "\n" +
                        "Lojista, doravante designada “CONTRATANTE”, representada neste instrumento pelo usuário,\n" +
                        "\n" +
                        "VEGAN, pessoa jurídica de direito privado, com sede na rua Dr. Osório de Araújo Ramos, 300, bairro 13 de Julho, CEP 49020-700, Aracaju/SE, inscrita no CNPJ   50.825.761/0001-50, representada neste instrumento por André Biriba Teixeira, microempreendedor, CPF 052.273.445-66, RG 34162100 SSP/SE, residente e domiciliado na rua Dr. Osório de Araújo Ramos, 300, bairro 13 de Julho, CEP 49020-700, Aracaju/SE, doravante designada “CONTRATADA”,\n" +
                        "\n" +
                        "também designadas, individualmente, como “parte”, e, coletivamente, como “partes”.\n" +
                        "\n" +
                        "Considerando que a CONTRATANTE é empresa com plena capacitação na área de produtos naturais;\n" +
                        "Considerando que a CONTRATADA dedica-se à agência de serviços de restaurantes e outros estabelecimentos similares e/ou prestadores de serviços de conveniências diversas, entre outros;\n" +
                        "Considerando que a CONTRATADA funcionará como intermediária entre a CONTRATANTE e o consumidor final, de modo que este terá acesso aos serviços cadastrados da CONTRATANTE na plataforma da CONTRATADA;\n" +
                        "Considerando que a CONTRATANTE receberá as demandas do consumidor final por intermédio da CONTRATADA, e assim prestará o serviço de entrega, ficando a CONTRATADA responsável pela recepção e encaminhamento dos pedidos para a CONTRATANTE.\n" +
                        "As partes resolvem celebrar este contrato de intermediação de negócios e outras avenças, com exceção da prestação do serviço de entrega para o consumidor final, conforme cláusulas e condições a seguir:\n" +
                        "CLÁUSULA PRIMEIRA – DO OBJETO\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "veganappbr\tveganosapp@gmail.com\t(79) 99953 7505\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "1.1 A CONTRATADA compromete-se a ofertar um cardápio inteligente e proporcionar o intermédio entre a CONTRATANTE e o consumidor final que desejam transacionar produtos livres de matérias-primas de origem animal, por meio do aplicativo marketplace Vegan, nos termos e condições ora ajustados e conforme legisçação brasileira aplicável.\n" +
                        "\n" +
                        "1.2 Como condição para receber as demandas do cardápio inteligente, a CONTRATANTE compromete-se a cumprir com os seguintes requisitos de qualificação:\n" +
                        "(i). Prestar os serviços intermediados pela CONTRATADA com qualidade, transparência e boa-fé, corrigindo, no menor tempo possível, quaisquer ocorrências adversas comunicadas pela CONTRATADA ou por algum usuário do Vegan;\n" +
                        "(ii). Manutenção dos produtos cadastrados na plataforma da CONTRATADA, garantindo a disponibilidade dos produtos na loja virtual;\n" +
                        "(iii). Cadastrar informações corretas e fidedignas, sendo responsabilidade da CONTRATANTE checar os dados informados;\n" +
                        "\n" +
                        "1.3 A CONTRATANTE reconhece e concorda que os serviços de marketplace a serem intermediados pela CONTRATADA serão prestados de acordo com as condições de porcentagem e forma de pagamento descritas a seguir.\n" +
                        "\n" +
                        "1.3.1 A CONTRATADA receberá a porcentagem de 10% (dez por cento) sob o valor de cada pedido efetuado. \n" +
                        "\n" +
                        "1.3.2 Em caso de funcionamento internacional, a taxa é de 15% sob o valor de cada pedido efetudo.\n" +
                        "\n" +
                        "1.4 Os serviços de marketplace não serão prestados em caráter de exclusividade, de modo que o IFOOD poderá intermediar serviços de cardápio inteligente juntamente à outras empresas.\n" +
                        "\n" +
                        "1.5 A CONTRATADA disponibiliza formas de pagamento presencial e online. Nesta forma, configura-se vendas online aquelas realizadas pelo aplicativo Vegan, apenas na opção “cartão de crédito/débito”. Naquela forma, o consumidor final poderá optar por pagar presencialmente por meio de dinheiro em espécie, pix ou cartão de crédito/débito na maquineta do estabelecimento comercial da CONTRATANTE.\n" +
                        "\n" +
                        "1.6 A CONTRATADA apenas se responsabiliza pelos valores percebidos por meio de cartão de crédito/débito no aplicativo Vegan. Os valores recebidos diretamente pela CONTRATANTE (dinheiro, pix ou cartão de crédito/débito na maquineta) no momento da entrega do produto será de responsabilidade da CONTRATANTE.\n" +
                        "\n" +
                        "1.7 A CONTRATANTE não está sujeita ao pagamento de assinatura mensal pelo uso do aplicativo, em caráter excepcional se convidada através da carta convite vitalício. \n" +
                        "\n" +
                        "CLÁUSULA SEGUNDA – DO REPASSE\n" +
                        "\n" +
                        "2.1  A CONTRATADA é responsável por repassar a percentagem dos valores recebidos por cartão de crédito/débito de forma mensal, até o 15º dia útil de cada mês subsequente, sob pena de multa de 2% a.m. se os valores dos ganhos das vendas na entrega não excederem os mesmos.\n " +
                        "A CONTRATANTE é responsável por repassar a percentagem dos valores recebidos na entrega de forma mensal, até o 15º dia útil de cada mês subsequente, sob pena de multa de 2% a.m. se os valores dos ganhos das vendas no app naquele mês não excederem os mesmos." +
                        " \n" +
                        "2.2 Os valores serão repassados por meio de transferência bancária ou pix, conforme dados bancários informados pela CONTRATANTE na plataforma digital.\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "2.3 O comprovante de pagamento do repasse será enviado ao email cadastrado pela CONTRATANTE na plataforma digital.\n" +
                        "\n" +
                        "2.4 No caso de valores percebidos diretamente pela CONTRATANTE (dinheiro em espécie, pix ou cartão de crédito/débito na maquineta), é de responsabilidade da CONTRATANTE repassar à CONTRATADA a porcentagem de 10% (dez por cento) sob o valor de cada compra, de forma mensal, até o 15º dia útil de cada mês seguinte, sob pena de multa de 2% a.m, se os valores dos ganhos das vendas no cartão no app naquele mês não excederem os mesmos. nos dados bancários que seguem:\n" +
                        "\n" +
                        "•  Banco: Next 237\n" +
                        "   André Biriba Teixeira\n" +
                        "   CPF: 052.273.445-66\n" +
                        "   Conta: 365763-9\n" +
                        "   Agência 3925\n" +
                        "   \n" +
                        "2.5 A CONTRATANTE deverá enviar o comprovante de pagamento referente a cláusula acima para o email veganosapp@gmail.com.\n" +
                        "\n" +
                        "2.6 Em caso de  ‘A quantia que se deve receber' exceder o ‘A quantia que se deve pagar' em determinado mês para o lojista, após a conclusão deste mesmo mês, haverá apenas a necessidade de transferência da CONTRATADA para a CONTRATANTE, com a devida subtração por parte do Vegan. No caso oposto, de que o ‘A quantia que se deve pagar' exceda o 'A quantia que se deve receber' haverá somente a necessidade de pagamento por parte da CONTRATANTE para a CONTRATADA com a devida subtração pela lojista. Comprovantes precisam ser enviados para os respectivos contatos eletrônicos e o atraso até o dia 15º util de cada mês seguinte, sofre pena de multa de 2% a.m. .\n" +
                        "\n" +
                        "\n" +
                        "CLÁUSULA TERCEIRA – DAS OBRIGAÇÕES DAS PARTES \n" +
                        "\n" +
                        "3.1 Sem prejuízo de outras obrigações estipuladas nesse contrato, a CONTRATANTE se obriga, por meio deste instrumento, a:\n" +
                        "\n" +
                        "(i) Repassar os valores recebidos no momento da entrega do produto (dinheiro em espécie, pix e cartão de crédito/débito), no final de cada mês, até o 5º da cláusula 2.1.\n" +
                        "(ii) Responsabilizar-se pelo zelo e segurança no preparo e e transporte do produto, evitando danos aos consumidores finais;\n" +
                        "(iii) É de responsabilidade da CONTRATANTE arcar com qualquer prejuízo que venha a ser causado pela CONTRATANTE, seja no preparo ou transporte do produto.\n" +
                        "(iv) Obedecer, por si só e por seus funcionários, todas as normas e regimentos internos de seguraça adotados pela CONTRATADA.\n" +
                        "(v) Obedecer, por si só e por seus funcionários, a legislação brasileira vigente, nos ditames legais, a exemplo do Código de Defesa do Consumidor.\n" +
                        "\n" +
                        "\n" +
                        "3.1 Sem prejuízo de outras obrigações estipuladas nesse contrato, a CONTRATADA se obriga, por meio deste instrumento, a:\n" +
                        "\n" +
                        "(i) Repassar os valores recebidos por meio do aplicativo (cartão de crédito/débito), no final de cada mês, até o 5º da cláusula 2.1.\n" +
                        "(ii) Possibilitar a oferta de serviço de marketplace, intermediando a transação entre a CONTRATANTE e os consumidores finais;\n" +
                        "(iii) Obedecer, por si só e por seus funcionários, a legislação brasileira vigente, nos ditames legais, a exemplo do Código de Defesa do Consumidor.\n" +
                        "\n" +
                        "\n" +
                        "CLÁUSULA QUARTA – PRAZO DE DURAÇÃO \n" +
                        "\n" +
                        "4.1 Este contrato terá prazo de duração indeterminado, podendo ser rescindido por qualquer das partes, com a antecedência de 30 (trinta) dias após a comunicação por escrita, podendo ser pela via eletrônica.\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "4.2 No caso da CONTRATANTE não manter sua loja virtual ativa no período de 90 (noventa) dias, estará sujeita a rescisão contratual.\n" +
                        "\n" +
                        "CLÁUSULA QUINTA – DO INADIMPLEMENTO CONTRATUAL\n" +
                        "\n" +
                        "5.1 No caso de uma das partes se tornar inadimplente, por ação ou omissão, no tocante a um ou mais de seus deveres e obriçaões estipulados no presente instrumento contratual, a outra parte poderá notificá-la para que, no prazo de atribuído na notificação, não podendo ser inferior a 10 (dez) dias úteis, sane, conforme o caso, tal inadimplemento.\n" +
                        "\n" +
                        "5.2 Caso o inadimplemento não seja sanado no prazo estipulado na notificação, a parte infratora deverá pagar multa de 2%, prevista no item 2.1.\n" +
                        "\n" +
                        "\n" +
                        "CLÁUSULA SEXTA – DA RESCISÃO\n" +
                        "\n" +
                        "6.1 Se, no prazo estipulado do item 2.1, a parte inadimplente não sanar o inadimplemento contratual, a outra parte poderá, sem prejuízo, rescindir estre contrato, notificando tal parte inadimplente, para informar a cessão completa e imediata dos efeitos do contrato.\n" +
                        "\n" +
                        "6.2 Fica desde já certo e ajustado que os serviços de marktplace serão rescindidos de forma automática (sem a necessidade de notificação da outra outra), nas hipóteses a seguir:\n" +
                        "\n" +
                        "Paralisação dos serviços da CONTRATANTE;\n" +
                        "Má-fé infrigida por alguma das partes em omitir informações de natureza fundamental para a continuidade da parceria entre a CONTRATANTE e a CONTRATADA;\n" +
                        "Transgressão de norma de qualquer natureza detectada em fiscalização por autoridades competentes;\n" +
                        "Alteração da finalidade comercial.\n" +
                        "\n" +
                        "\n" +
                        "CLÁUSULA SÉTIMA \n" +
                        "\n" +
                        "7.1 A relação jurídica estabelecida por entre contrato é de marketplace entre Vegan e Mercato Verde Produtos Naturais Ltda, de modo que este contrato não estabelece vínculo empregatício ou qualquer outra natureza entre as pessoas que colaboram com uma das partes e a outra parte, sendo certo que as partes são autônomas e independentes entre si.\n" +
                        "\n" +
                        "CLÁUSULA OITAVA – DAS DISPOSIÇÕES GERAIS\n" +
                        "\n" +
                        "8.1 Este contrato é celebrado em caráter irrevogável e irretratável, obrigando as partes e seus sucessores, seja qual for o título de sucessão.\n" +
                        "\n" +
                        "8.2 Este contrato somente poderá ser alterado mediante documento escrito, assinado pelas partes, na presenta de 2 (duas) testemunhas.\n" +
                        "\n" +
                        "CLÁUSULA NONA – TERMOS DE USO E POLÍTICA DE PRIVACIDADE\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "Em caso de modificação dos nossos termos de uso, publicaremos o novo texto no aplicativo, com a data de revisão atualizada.\n" +
                        "\n" +
                        "* Seção 1 - Usuário - A utilização deste aplicativo atribui de forma automática a condição de Usuário e implica a plena aceitação de todas as diretrizes e condições incluídas nestes Termos.\n" +
                        "* Seção 2 - Adesão em conjunto com a Política de Privacidade - A utilização deste aplicativo acarreta a adesão aos presentes Termos de Uso e a versão mais atualizada da Política de Privacidade de Vegan.\n" +
                        "* Seção 3 - Condições de acesso - Em geral, o acesso ao aplicativo do Vegan como lojista possuirá, excepcionalmente, caráter gratuito e exigirá prévia inscrição ou registro de formulário. Contudo, para usufruir de algumas funcionalidades, o usuário irá precisar além do formulário, terminar o cadastro, no próprio aplicativo com o email de acesso fornecido no formulário e a senha enviada pela equipe Vegan posteriormente. Esta senha poderá ser redefinida a qualquer momento, e todas as informações poderão ser editadas no aplicativo posteriormente, exceto o tipo de loja (dieta, vestuário, farmácia, outros) e o próprio email. É de total responsabilidade do usuário fornecer apenas informações corretas, autênticas, válidas, completas e atualizadas, bem como não divulgar o seu login e senha para terceiros. Vegan não consente com a publicação de conteúdos que tenham natureza discriminatória, ofensiva ou ilícita, ou ainda infrinjam direitos de autor ou quaisquer outros direitos de terceiros.\n" +
                        "* Seção 4 - Propriedade Intelectual - Todos os elementos de Vegan são de propriedade intelectual desta ou de seus licenciados. Estes Termos ou a utilização do aplicativo não concede a você qualquer licença ou direito de uso dos direitos de propriedade intelectual do Vegan ou de terceiros.\n" +
                        "* Seção 5 - Links para sites de terceiros - Este aplicativo poderá, de tempos a tempos, conter links de hipertexto que redirecionará você para sites das redes dos nossos parceiros, anunciantes, fornecedores etc. Se você clicar em um desses links para qualquer um desses sites, lembre-se que cada site possui as suas próprias práticas de privacidade e que não somos responsáveis por essas políticas. Consulte as referidas políticas antes de enviar quaisquer Dados Pessoais para esses sites. Não nos responsabilizamos pelas políticas e práticas de coleta, uso e divulgação (incluindo práticas de proteção de dados) de outras organizações, tais como Facebook, Apple, Google, Microsoft, ou de qualquer outro desenvolvedor de software ou provedor de aplicativo, loja de mídia social, sistema operacional, prestador de serviços de internet sem fio ou fabricante de dispositivos, incluindo todos os Dados Pessoais que divulgar para outras organizações por meio dos aplicativos, relacionadas a tais aplicativos, ou publicadas em nossas páginas em mídias sociais. Nós recomendamos que você se informe sobre a política de privacidade e termos de uso de cada site visitado ou de cada prestador de serviço utilizado.\n" +
                        "* Seção 6 - Dados pessoais Durante a utilização deste aplicativo, certos dados pessoais serão coletados e tratados por Vegan e/ou pelos Parceiros. As regras relacionadas ao tratamento de dados pessoais de Vegan estão estipuladas na Política de Privacidade.\n" +
                        "\n" +
                        "A sua privacidade é importante para nós. É política do Vegan respeitar a sua privacidade em relação a qualquer informação sua que possamos coletar, e outras aplicações que possuímos e operamos.\n" +
                        "Solicitamos informações pessoais apenas quando realmente precisamos delas para lhe fornecer um serviço. Fazemo-lo por meios justos e legais, com o seu conhecimento e consentimento. Também informamos por que estamos coletando e como será usado.\n" +
                        "Apenas retemos as informações coletadas pelo tempo necessário para fornecer o serviço solicitado. Quando armazenamos dados, protegemos dentro de meios comercialmente aceitáveis, para evitar perdas e roubos, bem como acesso, divulgação, cópia, uso ou modificação não autorizados.\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "Não compartilhamos informações de identificação pessoal publicamente ou com terceiros, exceto quando exigido por lei.\n" +
                        "O nosso aplicativo pode ter links para sites externos que não são operados por nós. Esteja ciente de que não temos controle sobre o conteúdo e práticas desses sites e não podemos aceitar responsabilidade por suas respectivas políticas de privacidade.\n" +
                        "\n" +
                        "O Vegan funciona apenas como cardápio inteligente entre os lojistas e o consumidor final e como sistema de gerenciamento de pedidos, não se responsabilizando pela realização da entrega ou devolução. É necessário pedir o código de entrega em todos os pedidos para prevenir fraude e assegurar que a entrega do produto foi realmente realizada para a plataforma Vegan. \n" +
                        "\n" +
                        "O usuário se compromete a fazer uso adequado dos conteúdos e da informação que o Vegan oferece no aplicativo e com caráter enunciativo, mas não limitativo:\n" +
                        "\n" +
                        "A) Não se envolver em atividades que sejam ilegais ou contrárias à boa fé a à ordem pública;\n" +
                        "B) Não difundir propaganda ou conteúdo de natureza racista, xenofóbica, qualquer tipo de pornografia ilegal, de apologia ao terrorismo ou contra os direitos humanos;\n" +
                        "C) Não causar danos aos sistemas físicos (hardwares) e lógicos (softwares) do Vegan app, de seus fornecedores ou terceiros, para introduzir ou disseminar vírus informáticos ou quaisquer outros sistemas de hardware ou software que sejam capazes de causar danos anteriormente mencionados.\n" +
                        "Esperemos que esteja esclarecido e, se houver algo que você não tem certeza se precisa ou não, geralmente é mais seguro deixar ativado, caso interaja com um dos recursos que você usa em nosso aplicativo.");

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        seller_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countryDialog();
            }
        });

        seller_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateDialog1();
            }
        });

        seller_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityDialog2();
            }
        });

        shopPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopPhoto.setClickable(false);
                showImagePickDialog();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginAdm.class));
            }
        });

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPrice();
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bxDiet.isChecked()) {
                    type = "Diet";
                }
                if (bxPharma.isChecked()) {
                    type = "Pharmacy";
                }
                if (bxCloset.isChecked()) {
                    type = "Closet";
                }
                if (bxOthers.isChecked()) {
                    type = "Others";
                }
                if (bxServices.isChecked()) {
                    type = "Services";
                }
                if (Objects.equals(type, "Services")) {


                    billingClient.startConnection(new BillingClientStateListener() {
                        @Override
                        public void onBillingServiceDisconnected() {

                        }

                        @Override
                        public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                            List<QueryProductDetailsParams.Product> productList = List.of(
                                    QueryProductDetailsParams.Product.newBuilder()
                                            .setProductId("services_monthly_subscription")//Play Console, Subscription Details, productID monthly_subscription
                                            .setProductType(BillingClient.ProductType.SUBS)
                                            .build()
                            );
                            QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                                    .setProductList(productList)
                                    .build();
                            billingClient.queryProductDetailsAsync(params, (billingResult1, productDetailsList) -> {
                                for (ProductDetails productDetails : productDetailsList) {
                                    String offerToken = productDetails.getSubscriptionOfferDetails()
                                            .get(0).getOfferToken();
                                    List<BillingFlowParams.ProductDetailsParams> productDetailsParamsList = List.of(
                                            BillingFlowParams.ProductDetailsParams.newBuilder()
                                                    .setProductDetails(productDetails)
                                                    .setOfferToken(offerToken)
                                                    .build()
                                    );
                                    BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                                            .setProductDetailsParamsList(productDetailsParamsList)
                                            .build();
                                    billingClient.launchBillingFlow(RegisterSellerActivity.this, billingFlowParams);
                                }

                            });


                        }
                    });

                }

                else {
                    billingClient.startConnection(new BillingClientStateListener() {
                        @Override
                        public void onBillingServiceDisconnected() {

                        }

                        @Override
                        public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                            List<QueryProductDetailsParams.Product> productList = List.of(
                                    QueryProductDetailsParams.Product.newBuilder()
                                            .setProductId("monthly_subscription")//Play Console, Subscription Details, productID monthly_subscription
                                            .setProductType(BillingClient.ProductType.SUBS)
                                            .build()
                            );
                            QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                                    .setProductList(productList)
                                    .build();
                            billingClient.queryProductDetailsAsync(params, (billingResult1, productDetailsList) -> {
                                for (ProductDetails productDetails : productDetailsList) {
                                    String offerToken = productDetails.getSubscriptionOfferDetails()
                                            .get(0).getOfferToken();
                                    List<BillingFlowParams.ProductDetailsParams> productDetailsParamsList = List.of(
                                            BillingFlowParams.ProductDetailsParams.newBuilder()
                                                    .setProductDetails(productDetails)
                                                    .setOfferToken(offerToken)
                                                    .build()
                                    );
                                    BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                                            .setProductDetailsParamsList(productDetailsParamsList)
                                            .build();
                                    billingClient.launchBillingFlow(RegisterSellerActivity.this, billingFlowParams);
                                }

                            });


                        }
                    });



                }


            }
        });


    }

    private void getPrice() {


        if (Objects.equals(type, "Services")) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                billingClient.startConnection(new BillingClientStateListener() {
                    @Override
                    public void onBillingServiceDisconnected() {

                    }

                    @Override
                    public void onBillingSetupFinished(@NonNull BillingResult billingResult) {

                        List<QueryProductDetailsParams.Product> productList = List.of(
                                QueryProductDetailsParams.Product.newBuilder()
                                        .setProductId("services_monthly_subscription")//Play Console, Subscription Details, productID monthly_subscription
                                        .setProductType(BillingClient.ProductType.SUBS)
                                        .build());
                        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                                .setProductList(productList)
                                .build();

                        billingClient.queryProductDetailsAsync(
                                params,
                                new ProductDetailsResponseListener() {
                                    @Override
                                    public void onProductDetailsResponse(@NonNull BillingResult billingResult,
                                                                         @NonNull List<ProductDetails> productDetailsList) {
                                        for (ProductDetails productDetails : productDetailsList) {
                                            response = productDetails.getSubscriptionOfferDetails().get(0).getPricingPhases()
                                                    .getPricingPhaseList().get(0).getFormattedPrice();
                                            sku = productDetails.getName();
                                            String ds = productDetails.getDescription();
                                            des = sku + " " + ds + " " + " Price " + response;
                                        }
                                    }
                                }
                        );
                    }
                });

                runOnUiThread(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    txtPrice.setText("Price: " + response);
                    desc.setText(des);
                    tvSub.setText(sku);
                });
            }
        });
    } else{
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    billingClient.startConnection(new BillingClientStateListener() {
                        @Override
                        public void onBillingServiceDisconnected() {

                        }

                        @Override
                        public void onBillingSetupFinished(@NonNull BillingResult billingResult) {

                            List<QueryProductDetailsParams.Product> productList = List.of(
                                    QueryProductDetailsParams.Product.newBuilder()
                                            .setProductId("monthly_subscription")//Play Console, Subscription Details, productID monthly_subscription
                                            .setProductType(BillingClient.ProductType.SUBS)
                                            .build());
                            QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                                    .setProductList(productList)
                                    .build();

                            billingClient.queryProductDetailsAsync(
                                    params,
                                    new ProductDetailsResponseListener() {
                                        @Override
                                        public void onProductDetailsResponse(@NonNull BillingResult billingResult,
                                                                             @NonNull List<ProductDetails> productDetailsList) {
                                            for (ProductDetails productDetails : productDetailsList) {
                                                response = productDetails.getSubscriptionOfferDetails().get(0).getPricingPhases()
                                                        .getPricingPhaseList().get(0).getFormattedPrice();
                                                sku = productDetails.getName();
                                                String ds = productDetails.getDescription();
                                                des = sku + " " + ds + " " + " Price " + response;
                                            }
                                        }
                                    }
                            );
                        }
                    });

                    runOnUiThread(() -> {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        txtPrice.setText("Price: " + response);
                        desc.setText(des);
                        tvSub.setText(sku);
                    });
                }
            });
    }



    }

    private final PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
        @Override
        public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> Purchase) {
            if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && Purchase != null){
                for(Purchase purchase:Purchase){
                    handlePurchase(purchase);
                }
            } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
                sts.setText("Already Subscribed");
                btn_sub.setVisibility(View.GONE);
            } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED) {
                sts.setText("Feature nor supported");
            } else {
                Toast.makeText(getApplicationContext(),"Error" + billingResult.getDebugMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    };

    void handlePurchase(Purchase purchase) {
        ConsumeParams consumeParams =
                ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();

        ConsumeResponseListener listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                }
            }
        };
        billingClient.consumeAsync(consumeParams, listener);
        if(purchase.getPurchaseState()== Purchase.PurchaseState.PURCHASED){
            if(!verifyValidSignature(purchase.getOriginalJson(), purchase.getSignature())) {
                Toast.makeText(getApplicationContext(), "Error : invalid Purchase", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!purchase.isAcknowledged()) {
                AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();
                billingClient.acknowledgePurchase(acknowledgePurchaseParams, acknowledgePurchaseResponseListener);
                sts.setText("Subscribed");
                isSuccess = true;
                btn_sub.setVisibility(View.GONE);
            } else {
                sts.setText("Already_Subscribed");
                btn_sub.setVisibility(View.GONE);
            }
        } else if(purchase.getPurchaseState()==Purchase.PurchaseState.PENDING){
            sts.setText("Subscription PENDING");
        }
        else if (purchase.getPurchaseState()==Purchase.PurchaseState.UNSPECIFIED_STATE){
            sts.setText("UNSPECIFIED_STATE");
        }
    }

    AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener = new AcknowledgePurchaseResponseListener() {
        @Override
        public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                sts.setText("Subscribed");
                isSuccess = true;

                final String email = user_email.getText().toString().trim();
                String password = user_password.getText().toString().trim();
                final String name = user_name.getText().toString();
                final String neighborhood = user_address_neighborhood.getText().toString();
                final String street = user_address_street.getText().toString();
                final String number = user_address_number.getText().toString();
                final String reference = user_reference_point.getText().toString();
                final String city = seller_city.getText().toString();
                final String country = paiscountry;
                final String cep = user_reference_cep.getText().toString();

                final String state = seller_state.getText().toString();
                final String cel = user_phone.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    user_name.setError(getString(R.string.umoumaiscam));
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    user_email.setError(getString(R.string.umoumaiscam));
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    user_password.setError(getString(R.string.umoumaiscam));
                    return;
                }

                if (password.length() < 6) {
                    user_password.setError(getString(R.string.senhapequena));
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                if (bxDiet.isChecked()) {
                    type = "Diet";
                }
                if (bxPharma.isChecked()) {
                    type = "Pharmacy";
                }
                if (bxCloset.isChecked()) {
                    type = "Closet";
                }
                if (bxOthers.isChecked()) {
                    type = "Others";
                }
                if (bxServices.isChecked()) {
                    type = "Services";
                }


                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            final Uri Image;
                            final String timestamp = "" + System.currentTimeMillis();
                            String filePathAndName = "shop_icon/" + "" + timestamp;

                            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
                            storageReference.putFile(image_uri)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                            while (!uriTask.isSuccessful()) ;
                                            Uri downloadImageUri = uriTask.getResult();

                                            if (uriTask.isSuccessful()) {
                                                userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                                                DocumentReference documentReference = fStore.collection("Adm's").document(userID);
                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                hashMap.put("nationalFee", "");
                                                hashMap.put("importedFee", "");
                                                hashMap.put("addressProfile", "");
                                                hashMap.put("segunda", "");
                                                hashMap.put("terca", "");
                                                hashMap.put("quarta", "");
                                                hashMap.put("quinta", "");
                                                hashMap.put("sexta", "");
                                                hashMap.put("sabado", "");
                                                hashMap.put("domingo", "");
                                                hashMap.put("segundaAbriu", "");
                                                hashMap.put("tercaAbriu", "");
                                                hashMap.put("quartaAbriu", "");
                                                hashMap.put("quintaAbriu", "");
                                                hashMap.put("sextaAbriu", "");
                                                hashMap.put("sabadoAbriu", "");
                                                hashMap.put("domingoAbriu", "");
                                                hashMap.put("segundaFechou", "");
                                                hashMap.put("tercaFechou", "");
                                                hashMap.put("quartaFechou", "");
                                                hashMap.put("quintaFechou", "");
                                                hashMap.put("sextaFechou", "");
                                                hashMap.put("sabadoFechou", "");
                                                hashMap.put("domingoFechou", "");
                                                hashMap.put("correct", "");
                                                hashMap.put("dinheiroEnt", "");
                                                hashMap.put("pixEnt", "");
                                                hashMap.put("cartaoEnt", "");
                                                hashMap.put("shopIcon", "" + downloadImageUri);
                                                hashMap.put("photo", "");
                                                hashMap.put("photo1", "");
                                                hashMap.put("photo2", "");
                                                hashMap.put("photo3", "");
                                                hashMap.put("photo4", "");
                                                hashMap.put("photo5", "");
                                                hashMap.put("fName", name);
                                                hashMap.put("cel",cel);
                                                hashMap.put("city", city);
                                                hashMap.put("state", state);
                                                hashMap.put("country", country);
                                                hashMap.put("cep", cep);
                                                hashMap.put("email", email);
                                                hashMap.put("password", password);
                                                hashMap.put("type", type);
                                                hashMap.put("neighborhood", neighborhood);
                                                hashMap.put("street", street);
                                                hashMap.put("apagaraoadm", "");
                                                hashMap.put("numberOfProducts", "0");
                                                hashMap.put("premium", "premiumtrue");
                                                hashMap.put("possuiFidelidade", "false");
                                                hashMap.put("number", number);
                                                hashMap.put("addressProfile", "");
                                                hashMap.put("daytable", "");
                                                hashMap.put("timetable", "");
                                                hashMap.put("timeDelivery", "");
                                                hashMap.put("description", "");
                                                hashMap.put("reference", reference);
                                                hashMap.put("uid", Objects.requireNonNull(fAuth.getUid()));
                                                hashMap.put("doMail", "false");
                                                hashMap.put("doInternational", "false");
                                                hashMap.put("petfriendly", "false");
                                                hashMap.put("accessibility", "false");
                                                hashMap.put("deliveryFee", "");
                                                hashMap.put("deliveryFixo", "");
                                                hashMap.put("contrato", "");
                                                hashMap.put("contaBancaria", "");
                                                hashMap.put("totalRepassado", "");
                                                hashMap.put("totalEstornado", "");
                                                hashMap.put("vegan", "false");
                                                if (Objects.equals(type, "Services")) {
                                                    hashMap.put("city", city);
                                                    hashMap.put("state", state);
                                                    hashMap.put("country", country);
                                                    hashMap.put("timeId", "");
                                                    hashMap.put("uid", "" + fAuth.getUid());
                                                    hashMap.put("type", type);
                                                    hashMap.put("serviceTitle", name);
                                                    hashMap.put("cel", "");
                                                    hashMap.put("address1", "");
                                                    hashMap.put("address2", "");
                                                    hashMap.put("address3", "");
                                                    hashMap.put("categories", "");
                                                    hashMap.put("profilePhoto", "" + downloadImageUri);
                                                    hashMap.put("serviceId", "");
                                                    hashMap.put("estPrice", "");
                                                    hashMap.put("firstBio", "");
                                                    hashMap.put("secondBio", "");
                                                    hashMap.put("thirdBio", "");
                                                    hashMap.put("suggest1", "");
                                                    hashMap.put("suggest2", "");
                                                    hashMap.put("suggest3", "");
                                                    hashMap.put("photo1", "");
                                                    hashMap.put("photo2", "");
                                                    hashMap.put("photo3", "");
                                                    hashMap.put("photo4", "");
                                                    hashMap.put("photo5", "");
                                                    DocumentReference documentReference2 = fStore.collection("Adm's").document(userID);
                                                    documentReference2.set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void avoid) {
                                                                    Toast.makeText(RegisterSellerActivity.this, R.string.sucesso, Toast.LENGTH_SHORT).show();
                                                                    signin();
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                }
                                                            });
                                                } else {
                                                    documentReference.set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void avoid) {
                                                                    Toast.makeText(RegisterSellerActivity.this, R.string.sucesso, Toast.LENGTH_SHORT).show();
                                                                    signin();
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                }
                                                            });
                                                }
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisterSellerActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });




            }
        }
    };

    private boolean verifyValidSignature(String signedData, String signature) {
        try {
            String base64Key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo+RnBisqyHAxxmle19nnbgskJ7CZQGt+dJRnqNEf1DBOaBqNa63sGXWQ8ipk5TFW3Ulocme7m648NlLmvM1mYDfgXngkcPlHlcxB9052BhlmIA3ikH32Jf6DiPIo0gljYiGgcM2mIgeagJxlJq6YcJbPpUUEbFmQLqpIGxOj1JpaDuzKiG/sCWSr/kf/VWXIJ6bk0P0zDGt2CGJtxFRir9FyYFmvKGAWjFr6d4kRwmFACq0u+RkKuFommmpnkQXH4g+st5FWcdPhOJr4tkfrhhpP7S9204nZDpIR6FSBQ7aiOVXcfqfdPJZ3tDaOhr94IE2VohbgfW6WVvwPiVY5vwIDAQAB";
            return Security.verifyPurchase(base64Key, signedData, signature);
        } catch (IOException e){
            return false;
        }
    }

    private void signin(){
        final String email = user_email.getText().toString().trim();
        String password = user_password.getText().toString().trim();
        Intent i = new Intent(this, Profile.class);
        i.putExtra("nome", user_name.getText().toString());
        i.putExtra("tipo",  type);
        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    if (Objects.equals(type, "Services")){
                        Intent intent = new Intent ( RegisterSellerActivity.this, AdmServicesActivity.class );
                        startVibrate ( 90 );
                        ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation ( getApplicationContext ( ),
                                R.anim.mover_esquerda, R.anim.mover_esquerda );
                        ActivityCompat.startActivity ( RegisterSellerActivity.this, intent, actcompat.toBundle ( ) );

                    }
                    if (Objects.equals(type, "Diet")) {
                        Intent intent = new Intent(RegisterSellerActivity.this, AdmHome.class);
                        startVibrate(90);
                        ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                                R.anim.mover_esquerda, R.anim.mover_esquerda);
                        ActivityCompat.startActivity(RegisterSellerActivity.this, intent, actcompat.toBundle());
                    }
                    if (Objects.equals(type, "Pharmacy")) {
                        Intent intent = new Intent(RegisterSellerActivity.this, AdmHome.class);
                        startVibrate(90);
                        ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                                R.anim.mover_esquerda, R.anim.mover_esquerda);
                        ActivityCompat.startActivity(RegisterSellerActivity.this, intent, actcompat.toBundle());
                    }
                    if (Objects.equals(type, "Closet")) {
                        Intent intent = new Intent(RegisterSellerActivity.this, AdmHome.class);
                        startVibrate(90);
                        ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                                R.anim.mover_esquerda, R.anim.mover_esquerda);
                        ActivityCompat.startActivity(RegisterSellerActivity.this, intent, actcompat.toBundle());
                    }
                    if (Objects.equals(type, "Others")) {
                        Intent intent = new Intent(RegisterSellerActivity.this, AdmHome.class);
                        startVibrate(90);
                        ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                                R.anim.mover_esquerda, R.anim.mover_esquerda);
                        ActivityCompat.startActivity(RegisterSellerActivity.this, intent, actcompat.toBundle());
                    }
                }else{
                    Toast.makeText(RegisterSellerActivity.this, "Erro!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    public static float getImageSize(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
            cursor.moveToFirst();
            float imageSize = cursor.getLong(sizeIndex);
            cursor.close();
            return imageSize; // returns size in bytes
        }
        return 0;
    }

    private void cityDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
        builder.setTitle(R.string.selcid)
                .setItems(Constants.City, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        String category3 = Constants.City[which];
                        seller_city.setText(category3);
                    }
                })
                .show();
    }

    private void stateDialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
        builder.setTitle(R.string.selest)
                .setItems(Constants.States, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        String category2 = Constants.States[which];
                        seller_state.setText(category2);
                    }
                })
                .show();
    }

    private void countryDialog() {
         String[] Countries = {
                 getString(R.string.brasil),
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
        builder.setTitle(R.string.selpai)
                .setItems(Countries, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        String category1 = Countries[which];
                        seller_country.setText(category1);
                        String[] Countrys = {
                                "Brasil",
                        };
                        paiscountry = Countrys[which];
                    }
                })
                .show();
    }


    private void showImagePickDialog() {
        String[] options = {getString(R.string.camera), getString(R.string.galeria)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.escolhaum)
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which==0){
                            if(checkCameraPermission()){
                                pickFromCamera();
                            }
                            else {
                                requestCameraPermission();
                            }
                        }
                        else {
                            if(checkStoragePermission()){
                                pickFromGallery();
                            }
                            else {
                                requestStoragePermission();
                            }
                        }
                    }
                })
                .show();
    }

    private void pickFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE,"Temp_Image_Title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Image_Description");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if(resultCode == RESULT_OK){

            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                image_uri = data.getData();
                if(getImageSize(getApplicationContext(),image_uri)<250400) {
                    shopPhoto.setImageURI(image_uri);
                addPhotoDB();
                }else{
                    Toast.makeText(RegisterSellerActivity.this, R.string.bigimage, Toast.LENGTH_SHORT).show();
                }
            }
            else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                    if(getImageSize(getApplicationContext(),image_uri)<250400) {
                        shopPhoto.setImageURI(image_uri);
                addPhotoDB();
                    }else{
                        Toast.makeText(RegisterSellerActivity.this, R.string.bigimage, Toast.LENGTH_SHORT).show();
                    }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(billingClient!=null){
            billingClient.endConnection();
        }
    }

    private void addPhotoDB() {
    }

    public void onBackPressed ( ) {
        finish ( );
    }

    public void startVibrate ( long time ) {
        Vibrator atvib = ( Vibrator ) getSystemService ( Context.VIBRATOR_SERVICE );
        assert atvib != null;
        atvib.vibrate ( time );
    }

    public void getPrice_click(View view) {
    }

    public void btn_sub_click(View view) {
    }

    public void quit_click(View view) {
    }
}


