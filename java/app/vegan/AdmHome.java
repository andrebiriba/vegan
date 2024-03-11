package app.vegan;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryPurchasesParams;
import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class AdmHome extends AppCompatActivity implements Serializable {
    private BillingClient billingClient;
    boolean isSuccess= false;
    private ProgressDialog progressDialog;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;
    private String[] cameraPermissions;
    private String[] storagePermissions;
    private Uri image_uri;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private EditText brandNameTv;
    private TextView category, tempoEstimado, ajuda;
    ImageView imageButton9, imageButton10, promoBtn, imageButton7, imageButton11, points, statistic, editIv, shareIv, pencil
            ,imageclick0, imageclick1, imageclick2, imageclick3,imageclick4, imageclick5, imageclick6,imageclick7;
    ShapeableImageView shopPhoto;
    private ImageButton imageButtonBack, settingsBtn;
    private Button btn_subs;
    private String titleP, textP, temPush;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_home);
        setRequestedOrientation ( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        ajuda = findViewById(R.id.textView12);
        imageclick0 = findViewById(R.id.imageclick0);
        imageclick1 = findViewById(R.id.imageclick1);
        imageclick2 = findViewById(R.id.imageclick2);
        imageclick3 = findViewById(R.id.imageclick3);
        imageclick4 = findViewById(R.id.imageclick4);
        imageclick5 = findViewById(R.id.imageclick5);
        imageclick6 = findViewById(R.id.imageclick6);
        imageclick7 = findViewById(R.id.imageclick7);
        tempoEstimado = findViewById(R.id.textView17);
        shareIv = findViewById(R.id.shareIv);
        editIv = findViewById(R.id.editIv);
        btn_subs = findViewById(R.id.btn_subs);
        points = findViewById(R.id.imageView18);
        statistic = findViewById(R.id.imageView17);
        category = findViewById(R.id.textView16);
        brandNameTv = findViewById(R.id.textView15);
        shopPhoto = findViewById(R.id.imageView);
        settingsBtn = findViewById(R.id.settingsUp);
        promoBtn = findViewById(R.id.promoBtn);
        imageButton9 = findViewById(R.id.imageButton9);
        imageButton10 = findViewById(R.id.imageButton10);
        imageButton11 = findViewById(R.id.imageButton11);
        imageButton7 = findViewById(R.id.imageButton7);
        pencil = findViewById(R.id.pencil);

        fAuth = FirebaseAuth.getInstance();

        createNotificationChannel();

        ActivityCompat.requestPermissions(this,
                new String[]{CAMERA, READ_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);


        ActivityCompat.requestPermissions(this,
                new String[]{READ_MEDIA_IMAGES, WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ZYPUSH "+fAuth.getCurrentUser().getUid());
        ref
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        titleP = ""+snapshot.child("title").getValue();
                        textP = ""+snapshot.child("text").getValue();

                        int Emoji = 0x1F49A;

                        String stringEmoji = String.valueOf(Emoji);

                        stringEmoji = (new String(Character.toChars(Emoji)));

                        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                                new Intent(getApplicationContext(), AdmHome.class), PendingIntent.FLAG_IMMUTABLE);

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "lemubitA")
                                .setSmallIcon(R.drawable.veganosbrand)
                                .setContentTitle(titleP + stringEmoji )
                                .setContentIntent(contentIntent)
                                .setContentText(textP)
                                .setPriority(NotificationCompat.PRIORITY_HIGH);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

                        int mNotificationId = 001;

                        NotificationManager mNotificationManager =
                                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                        mNotificationManager.notify(mNotificationId, builder.build());

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

        billingClient = BillingClient.newBuilder(AdmHome.this)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();
        query_purchase();

        fStore = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.aguardeum);
        progressDialog.setCanceledOnTouchOutside(false);

        loadInfo();
        getToken();

        launcher=
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(ActivityResult result)->{
                    if(result.getResultCode()==RESULT_OK){
                        image_uri = result.getData().getData();
                        try {
                            compressImage();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }else if(result.getResultCode()==ImagePicker.RESULT_ERROR){
                        Toast.makeText(AdmHome.this, result.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        shareIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.sharenewshop);
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, "Compartilhar", null);
                Uri imageUri =  Uri.parse(path);
                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(Intent.createChooser(share, getString(R.string.selecione_o_tipo)));
            }
        });

        ajuda.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(AdmHome.this);
                builder.setTitle("Contrato");
                builder.setMessage("CONTRATO DE PRESTAÇÃO DE SERVIÇOS \n" +
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

        shopPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  shopPhoto.setClickable(false);
                showImagePickDialog();
            }
        });

        promoBtn = findViewById(R.id.promoBtn);

        promoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(AdmHome.this, ActivityPromotionCodes.class);
                startActivity(intentLoadNewActivity);
            }
        });

        pencil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (brandNameTv.isEnabled()){
                    brandNameTv.setEnabled(false);
                    pencil.setImageResource(R.drawable.ic_baseline_edit_24);
                    String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                    DocumentReference documentReference = fStore.collection("Adm's").document(userID);
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("fName", brandNameTv.getText().toString());
                    documentReference.update(hashMap).
                            addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void avoid) {
                                    Toast.makeText(AdmHome.this, "Nome de Loja Alterado!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AdmHome.this, "erro " + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    brandNameTv.setEnabled(true);
                    pencil.setImageResource(R.drawable.ic_baseline_save_24_orange);

                }
            }
        });

        imageclick7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(AdmHome.this, AllChatsActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });

        imageclick0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(AdmHome.this, Produtos.class);
                startActivity(intentLoadNewActivity);
            }
        });

        imageclick1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(AdmHome.this, AdmCadPro.class);
                startActivity(intentLoadNewActivity);
            }
        });

        imageclick2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(AdmHome.this, Pedidos.class);
                startActivity(intentLoadNewActivity);
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(AdmHome.this, SettingsActivity.class);
                intentLoadNewActivity.putExtra("adm", "true");
                startActivity(intentLoadNewActivity);            }
        });

        imageButtonBack = findViewById(R.id.imageButtonBack);

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intentLoadNewActivity = new Intent(AdmHome.this, LoginAdm.class);
                startActivity(intentLoadNewActivity);
            }
        });

        imageclick5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AdmHome.this, PointsSeller.class);
                intent.putExtra("brandName", brandNameTv.getText().toString());
                startActivity(intent);
            }
        });

        imageclick6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intentLoadNewActivity = new Intent(AdmHome.this, LookRestaurantStudio.class);
                startActivity(intentLoadNewActivity);
            }
        });

        imageclick4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intentLoadNewActivity = new Intent(AdmHome.this, Statistic.class);
                startActivity(intentLoadNewActivity);
            }
        });

        btn_subs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intentLoadNewActivity = new Intent(AdmHome.this, RegisterSellerActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });
    }

    private void compressImage() throws IOException {
   /*     Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,40,baos);

        File tempDir = Environment.getExternalStorageDirectory();
        tempDir=new File(tempDir.getAbsolutePath()+"/.temp01/");
        tempDir.mkdir();
        String title = "temp01";
        File tempFile = File.createTempFile(title,".jpg",tempDir);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
        byte[] bitmapData = bytes.toByteArray();

        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(bitmapData);
        fos.flush();
        fos.close();
        image_uri = Uri.fromFile(tempFile);*/
        shopPhoto.setImageURI(image_uri);
        addPhotoDB();

    }

    private final PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
        @Override
        public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> Purchase) {

        }
    };

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.aboutustxt);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("lemubitA", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void getToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if(task.isSuccessful()){
                            String token= task.getResult();
                            String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                            System.out.println("TOKEN "+ token);
                            DocumentReference docRef = fStore.collection("Adm's").document(userID);
                            Map<String,Object> edited = new HashMap<>();
                            edited.put("token",token);
                            docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AdmHome.this, R.string.possibletokene,
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                            return;
                        }

                    }
                });
    }

    private void query_purchase(){
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {

            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {

                if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    ExecutorService executorService= Executors.newSingleThreadExecutor();
                    executorService.execute(() -> {
                        try {
                            billingClient.queryPurchasesAsync(
                                    QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build(),
                            (billingResult1, purchaseList) -> {
                                        for(Purchase purchase:purchaseList){
                                            if(purchase!=null && purchase.isAcknowledged()){
                                                isSuccess = true;
                                                DocumentReference docRef = fStore.collection("Adm's").document(fAuth.getCurrentUser().getUid());
                                                Map<String,Object> edited = new HashMap<>();
                                                edited.put("premium","premiumtrue");
                                                docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                    }
                                                });
                                            }
                                            else {
                                                //notsubscribed
                                                DocumentReference docRef = fStore.collection("Adm's").document(fAuth.getCurrentUser().getUid());
                                                Map<String,Object> edited = new HashMap<>();
                                                edited.put("premium","premiumfalse");
                                                docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                    }
                                                });


                                            }
                                        }
                            }
                            );
                        } catch (Exception ex){
                            //is Premium=false;
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if(isSuccess){
                                    ConnectionClass.premium= true;
                                    ConnectionClass.removeAds= true;
                                }
                            }
                        });

                    });
                }

            }
        });
    }


    private void loadInfo() {
        String userID;
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Adm's").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String brandIcon = document.getString("shopIcon");
                        String brandName = document.getString("fName");
                        String timeDelivery = document.getString("timeDelivery");
                        String type = document.getString("type");
                        String contratovitalicio = document.getString("contrato");
                        Log.d(TAG, "contratovitalicio "+contratovitalicio);

                        if(Objects.equals(contratovitalicio, "false")){
                            AlertDialog.Builder veglord = new AlertDialog.Builder(AdmHome.this, R.style.MyAlertDialogTheme);
                            veglord.setTitle("Contrato Convite");
                            veglord.setMessage("CONTRATO DE PRESTAÇÃO DE SERVIÇOS \n" +
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
                            veglord.setPositiveButton("Li e Aceito", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DocumentReference documentReference = fStore.collection("Adm's").document(userID);
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("contrato", "true");
                                    documentReference.update(hashMap).
                                            addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void avoid) {
                                                    Toast.makeText(AdmHome.this, "Contrato Aceito.", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(AdmHome.this, "erro " + e, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                            veglord.create().show();



                        }


                        switch (Objects.requireNonNull(type)) {
                            case "Diet":
                                category.setText("Dieta");
                                break;
                            case "Pharmacy":
                                category.setText("Farmácia");
                                break;
                            case "Closet":
                                category.setText("Vestuário");
                                break;
                            case "Others":
                                category.setText("Outros");
                                break;
                        }

                        tempoEstimado.setText(timeDelivery);

                        brandNameTv.setText(brandName);
                        try {
                            Picasso.get().load(brandIcon).placeholder(R.drawable.loading).into(shopPhoto);
                        } catch (Exception e) {
                            shopPhoto.setImageResource(R.drawable.error);
                        }
                    }
                }
            }
        });
    }

    private void showImagePickDialog() {

        ImagePicker.Companion.with(AdmHome.this)
                .crop()
                .cropOval()
                .maxResultSize(512,512,true)
                .provider(ImageProvider.BOTH) //Or bothCameraGallery()
                .createIntentFromDialog((Function1)(new Function1(){
                    public Object invoke(Object var1){
                        this.invoke((Intent)var1);
                        return Unit.INSTANCE;
                    }


                    public final void invoke(@NotNull Intent it){
                        Intrinsics.checkNotNullParameter(it,"it");
                        launcher.launch(it);
                    }

                }));
    }

    public static float getImageSize(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
            cursor.moveToFirst();
            float imageSize = cursor.getLong(sizeIndex);
            cursor.close();
            return imageSize;
        }
        return 0;
    }

    private void addPhotoDB() {
        progressDialog.setMessage(getString(R.string.aguardeum));
        progressDialog.show();
        final String timestamp = ""+System.currentTimeMillis();
        String userID;
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        String filePathAndName = "shop_icon/" + "" + timestamp;

        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
        storageReference.putFile(image_uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isSuccessful());
                        Uri downloadImageUri = uriTask.getResult();

                        if (uriTask.isSuccessful()){
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("shopIcon",""+downloadImageUri);
                            DocumentReference documentReference = fStore.collection("Adm's").document(userID);
                            documentReference.update(hashMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void avoid){
                                            progressDialog.dismiss();
                                            Toast.makeText(AdmHome.this, R.string.fotoad, Toast.LENGTH_SHORT).show();
                                            shopPhoto.setClickable(true);
                                            loadInfo();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            shopPhoto.setClickable(true);
                                            Toast.makeText(AdmHome.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(AdmHome.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void onBackPressed ( ) {
        finish ( );
    }
}
